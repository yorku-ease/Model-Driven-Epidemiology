"""Quality checks: Reuse Phase 1 analyzers on extracted model

Runs Phase 1 model analyzer, uncertainty analyzer, and sensitivity analyzer
on the extracted model.
"""

import json
import subprocess
import sys
from pathlib import Path
from typing import Dict, Any, Optional


class QualityChecker:
    """Run Phase 1 analyzers on extracted model"""
    
    def __init__(self, phase1_dir: Optional[str] = None):
        """
        Initialize quality checker.
        
        Args:
            phase1_dir: Path to Phase 1 directory (for analyzer imports)
        """
        self.phase1_dir = Path(phase1_dir) if phase1_dir else None
    
    def run_model_analyzer(self, compmodel_path: str, model_name: str) -> Optional[Dict[str, Any]]:
        """
        Run Phase 1 model analyzer.
        
        Returns:
            Model analysis results or None if failed
        """
        if not self.phase1_dir:
            print("Warning: Phase 1 directory not specified, skipping model analysis")
            return None
        
        analyzer_path = self.phase1_dir / "analysis" / "model_analyzer.py"
        if not analyzer_path.exists():
            print(f"Warning: Model analyzer not found at {analyzer_path}")
            return None
        
        try:
            # Run model analyzer as subprocess
            result = subprocess.run(
                [sys.executable, str(analyzer_path),
                 "--model-file", compmodel_path,
                 "--model-name", model_name,
                 "--output-dir", str(Path(compmodel_path).parent)],
                capture_output=True,
                text=True,
                timeout=60
            )
            
            if result.returncode == 0:
                # Try to load the generated JSON
                output_dir = Path(compmodel_path).parent
                json_path = output_dir / f"{model_name.lower().replace('-', '_')}_analysis.json"
                if json_path.exists():
                    with open(json_path, 'r') as f:
                        return json.load(f)
            else:
                print(f"Warning: Model analyzer failed: {result.stderr}")
        except Exception as e:
            print(f"Warning: Failed to run model analyzer: {e}")
        
        return None
    
    def run_uncertainty_analyzer(self, compmodel_path: str, model_name: str) -> Optional[Dict[str, Any]]:
        """
        Run Phase 1 uncertainty analyzer.
        
        Returns:
            Uncertainty analysis results or None if failed
        """
        if not self.phase1_dir:
            print("Warning: Phase 1 directory not specified, skipping uncertainty analysis")
            return None
        
        analyzer_path = self.phase1_dir / "analysis" / "uncertainty_analyzer.py"
        if not analyzer_path.exists():
            print(f"Warning: Uncertainty analyzer not found at {analyzer_path}")
            return None
        
        try:
            result = subprocess.run(
                [sys.executable, str(analyzer_path),
                 "--model-file", compmodel_path,
                 "--model-name", model_name,
                 "--output-dir", str(Path(compmodel_path).parent)],
                capture_output=True,
                text=True,
                timeout=60
            )
            
            if result.returncode == 0:
                output_dir = Path(compmodel_path).parent
                json_path = output_dir / f"{model_name.lower().replace('-', '_')}_uncertainty.json"
                if json_path.exists():
                    with open(json_path, 'r') as f:
                        return json.load(f)
        except Exception as e:
            print(f"Warning: Failed to run uncertainty analyzer: {e}")
        
        return None

    def run_sensitivity_analyzer(self, compmodel_path: str, model_name: str) -> Optional[Dict[str, Any]]:
        """
        Run Phase 1 sensitivity analysis.

        Returns:
            Sensitivity analysis results or None if failed
        """
        if not self.phase1_dir:
            print("Warning: Phase 1 directory not specified, skipping sensitivity analysis")
            return None

        analyzer_path = self.phase1_dir / "analysis" / "sensitivity_analysis.py"
        if not analyzer_path.exists():
            print(f"Warning: Sensitivity analyzer not found at {analyzer_path}")
            return None

        try:
            # Use Morris method by default (recommended in Phase 1 script)
            result = subprocess.run(
                [
                    sys.executable,
                    str(analyzer_path),
                    "--model-file",
                    compmodel_path,
                    "--model-name",
                    model_name,
                    "--output-dir",
                    str(Path(compmodel_path).parent),
                    "--method",
                    "morris",
                ],
                capture_output=True,
                text=True,
                timeout=300,
            )

            if result.returncode == 0:
                output_dir = Path(compmodel_path).parent
                json_path = (
                    output_dir
                    / f"{model_name.lower().replace('-', '_')}_sensitivity_morris.json"
                )
                if json_path.exists():
                    with open(json_path, "r") as f:
                        return json.load(f)
                else:
                    # If the script ran but produced a different message, log stderr/stdout
                    if result.stderr:
                        print(f"Warning: Sensitivity analyzer stderr: {result.stderr}")
            else:
                print(f"Warning: Sensitivity analyzer failed: {result.stderr}")
        except Exception as e:
            print(f"Warning: Failed to run sensitivity analyzer: {e}")

        return None
    
    def check_model_quality(self, compmodel_path: str, model_name: str) -> Dict[str, Any]:
        """
        Run all quality checks.
        
        Returns:
            Dictionary with all quality check results
        """
        results = {
            "model_analysis": None,
            "uncertainty_analysis": None,
            "sensitivity_analysis": None,
            "status": {}
        }
        
        # Run model analyzer
        model_analysis = self.run_model_analyzer(compmodel_path, model_name)
        results["model_analysis"] = model_analysis
        results["status"]["model_analysis"] = "completed" if model_analysis else "failed"
        
        # Run uncertainty analyzer
        uncertainty_analysis = self.run_uncertainty_analyzer(compmodel_path, model_name)
        results["uncertainty_analysis"] = uncertainty_analysis
        results["status"]["uncertainty_analysis"] = "completed" if uncertainty_analysis else "failed"
        
        # Sensitivity analysis (now integrated via Phase 1 sensitivity_analysis.py)
        sensitivity_analysis = self.run_sensitivity_analyzer(compmodel_path, model_name)
        results["sensitivity_analysis"] = sensitivity_analysis
        results["status"]["sensitivity_analysis"] = "completed" if sensitivity_analysis else "failed"
        
        return results
    
    def save_quality_report(self, results: Dict[str, Any], output_path: str):
        """Save quality check results to JSON"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(results, f, indent=2)
