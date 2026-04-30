"""Graphviz DOT export for feature_tree.json.

Renders the 17-flag EpiFeatureVector as a clean grid diagram using Graphviz
HTML-like table labels.  Each group (Transmission, Clinical, etc.) becomes a
column; each flag becomes a cell.  Active flags (True) are coloured and bold;
inactive flags are greyed out.

Two modes:
- Shared vocabulary diagram (no assignment): all cells shown in the group colour,
  neutral weight — used as a legend / vocabulary reference.
- Per-disease diagram (with assignment): True flags highlighted, False flags greyed.
"""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List, Optional, Set, Tuple

# ---------------------------------------------------------------------------
# Group definitions — order, display name, background colour, accent colour.
# Must match the feature_tree.json grouping.
# ---------------------------------------------------------------------------
_GROUPS: List[Tuple[str, str, str, str]] = [
    # (group_id, display_name, bg_colour, accent_colour)
    ("G_Transmission",   "Transmission routes",             "#daf0fc", "#5a9fc2"),
    ("G_ClinicalPath",   "Natural history &#38; pathogenesis", "#d4f5e8", "#4aaa7a"),
    ("G_Population",     "Population structure",             "#f9f5d9", "#c8a838"),
    ("G_NonHumanHosts",  "Non-human hosts",                  "#fae8fb", "#aa6ab8"),
    ("G_Demography",     "Demography",                       "#ede8fc", "#8877cc"),
]

# Human-readable short labels for each flag (used in diagram cells).
_FLAG_LABELS: Dict[str, str] = {
    "route_vector_arthropod":                 "Vector / Arthropod",
    "route_sexual_or_partner_network":        "Sexual / Partner",
    "route_airborne_or_respiratory_droplet":  "Airborne / Respiratory",
    "route_fecal_oral_or_waterborne":         "Fecal-oral / Waterborne",
    "route_bloodborne_vertical_or_parenteral":"Bloodborne / Vertical",
    "route_healthcare_general_contact":       "Healthcare Contact",
    "latent_or_exposed_class":                "Latent / Exposed",
    "multiple_infectious_stages_or_chronic":  "Staged / Chronic",
    "hospitalized_or_severity_stratification":"Hospitalized / Severity",
    "treatment_or_art_intervention":          "Treatment / ART",
    "vaccination_route_compartment":          "Vaccination",
    "recovered_or_immune_endpoint":           "Recovered / Immune",
    "stratification_demographic_roles":       "Demographic Stratification",
    "spatial_or_patch_like_naming":           "Spatial / Patch",
    "vector_or_intermediate_species_present": "Vector / Intermediate Spp.",
    "zoonotic_or_animal_compartment":         "Zoonotic / Animal",
    "recruitment_birth_or_immigration_named": "Birth / Recruitment",
}


def _header_cell(display_name: str, bg: str, accent: str) -> str:
    return (
        f'<TD BGCOLOR="{bg}" BORDER="0" ALIGN="CENTER" CELLPADDING="8">'
        f'<FONT FACE="Helvetica Neue" POINT-SIZE="11" COLOR="{accent}"><B>{display_name}</B></FONT>'
        f'</TD>'
    )


def _flag_cell(
    label: str,
    is_true: bool,
    bg: str,
    accent: str,
    *,
    per_disease: bool,
) -> str:
    if per_disease:
        if is_true:
            fill = bg
            border_col = accent
            border_w = "2"
            font_col = "#111111"
            font_size = "11"
            bold_o, bold_c = "<B>", "</B>"
        else:
            fill = "#f0f0f0"
            border_col = "#dddddd"
            border_w = "1"
            font_col = "#aaaaaa"
            font_size = "10"
            bold_o, bold_c = "", ""
    else:
        # Shared vocabulary: all shown equally in group colour
        fill = bg
        border_col = accent
        border_w = "1"
        font_col = "#333333"
        font_size = "10.5"
        bold_o, bold_c = "", ""

    return (
        f'<TD BGCOLOR="{fill}" BORDER="{border_w}" COLOR="{border_col}" '
        f'CELLPADDING="9" STYLE="rounded">'
        f'<FONT FACE="Helvetica Neue" POINT-SIZE="{font_size}" COLOR="{font_col}">'
        f'{bold_o}{label}{bold_c}</FONT>'
        f'</TD>'
    )


def _empty_cell() -> str:
    return '<TD BORDER="0" CELLPADDING="9"></TD>'


def _spacer_row(n_cols: int) -> str:
    return '<TR>' + ('<TD HEIGHT="5" BORDER="0"></TD>' * n_cols) + '</TR>'


def _build_table(
    tree: Dict[str, Any],
    *,
    true_flags: Optional[Set[str]],
) -> str:
    """Build the HTML table string for the diagram."""
    per_disease = true_flags is not None
    nodes = tree.get("nodes", {})
    n_cols = len(_GROUPS)

    # Collect flag lists per group
    group_flags: List[List[str]] = []
    for gid, _name, _bg, _acc in _GROUPS:
        children = [str(c) for c in nodes.get(gid, {}).get("children", [])]
        group_flags.append(children)

    max_rows = max(len(g) for g in group_flags)

    rows: List[str] = []

    # Header row
    header_cells = "".join(
        _header_cell(name, bg, acc)
        for _, name, bg, acc in _GROUPS
    )
    rows.append(f"<TR>{header_cells}</TR>")
    rows.append(_spacer_row(n_cols))

    # Flag rows
    for r in range(max_rows):
        cells = []
        for col_idx, (gid, _name, bg, acc) in enumerate(_GROUPS):
            flags = group_flags[col_idx]
            if r < len(flags):
                fid = flags[r]
                label = _FLAG_LABELS.get(fid, fid)
                is_true = (fid in true_flags) if per_disease else False
                cells.append(_flag_cell(label, is_true, bg, acc, per_disease=per_disease))
            else:
                cells.append(_empty_cell())
        rows.append(f"<TR>{''.join(cells)}</TR>")

    table_content = "".join(rows)
    return (
        f'<<TABLE BORDER="0" CELLBORDER="0" CELLSPACING="6" CELLPADDING="0">'
        f'{table_content}'
        f'</TABLE>>'
    )


def _build_title(*, disease_label: Optional[str]) -> str:
    if disease_label:
        heading = f"Feature Profile &#8212; {disease_label}"
        subtitle = (
            "Highlighted = True in canonical gold profile.&#160;&#160;"
            "Grey = not present in any gold model."
        )
    else:
        heading = "Epidemiological Feature Vocabulary"
        subtitle = (
            "The 17 Boolean flags used to describe any compartmental epidemic model. "
            "Per-disease diagrams highlight which flags are active for that disease."
        )

    return (
        f'<<TABLE BORDER="0" CELLBORDER="0" CELLSPACING="3">'
        f'<TR><TD ALIGN="LEFT"><FONT FACE="Helvetica Neue" POINT-SIZE="18">'
        f'<B>{heading}</B></FONT></TD></TR>'
        f'<TR><TD ALIGN="LEFT"><FONT FACE="Helvetica Neue" POINT-SIZE="10" COLOR="#666666">'
        f'{subtitle}</FONT></TD></TR>'
        f'</TABLE>>'
    )


def fm_tree_to_dot(
    tree: Dict[str, Any],
    *,
    true_flags: Optional[Set[str]] = None,
    disease_label: Optional[str] = None,
) -> str:
    """Render a Graphviz DOT string from the feature tree.

    Parameters
    ----------
    tree:
        Parsed feature_tree.json content.
    true_flags:
        Set of flag field names that are True for this disease.  When provided
        the diagram highlights active flags and greys out inactive ones.
        When None (shared vocabulary diagram) all flags are shown equally.
    disease_label:
        Display name for the disease shown in the diagram title.
    """
    table = _build_table(tree, true_flags=true_flags)
    title = _build_title(disease_label=disease_label)

    return (
        "digraph G {\n"
        f"  graph [bgcolor=\"white\" pad=\"0.7,0.5\" label={title} labelloc=t labeljust=l]\n"
        "  node [shape=none margin=0]\n"
        f"  main [label={table}]\n"
        "}\n"
    )


def export_dot(tree_path: Path, out_path: Path) -> None:
    """Export the shared vocabulary diagram (all flags shown equally)."""
    tree = json.loads(tree_path.read_text(encoding="utf-8"))
    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text(fm_tree_to_dot(tree), encoding="utf-8")


def export_disease_dot(
    tree_path: Path,
    out_path: Path,
    canonical_vector: Dict[str, Any],
    disease_slug: str,
) -> None:
    """Export a per-disease diagram highlighting only the True flags.

    Parameters
    ----------
    tree_path:
        Path to feature_tree.json.
    out_path:
        Where to write the .dot file.
    canonical_vector:
        The canonical_feature_vector dict for this disease.
    disease_slug:
        Used as the display label in the diagram title.
    """
    tree = json.loads(tree_path.read_text(encoding="utf-8"))
    true_flags: Set[str] = {
        k for k, v in canonical_vector.items()
        if v is True and k != "matched_signals"
    }
    label = disease_slug.replace("_", " ").title()
    dot = fm_tree_to_dot(tree, true_flags=true_flags, disease_label=label)
    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text(dot, encoding="utf-8")