/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.Batch;
import batchRateContactFlow.BatchRateContactFlowPackage;
import epimodel.util.PhysicalFlow;
import epimodel.Compartment;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Batch</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BatchImpl extends FromToFlowImpl implements Batch {

	@Override
	public void edit(Shell shell, List<Control> controls, Compartment target) {
//		shell.setText("Edit Flow " + getId() + " for compartment " + target.getLabel());
//		shell.setLayout(new GridLayout(2, false));
//		for (EReference ref : flowRefs()) {
//			epimodel.util.Edit.addText(shell, controls, ref.getName());
//			epimodel.util.Edit.addBtn(shell, controls, "Set '" + ref.getName() + "' to " + target.getLabel(), () -> {
//				epimodel.util.Edit.transact(this, () -> eSet(ref, target));
//				shell.close();
//			});
//		}
	}

	@Override
	public List<PhysicalFlow> getEquations() {
		String basic_coefficient = "(parameter " + getId() + " " + from.getLabels() + ") ";
		// todo other coefficients
		String equation = "(* " + basic_coefficient + " )";
		List<String> requiredOperators = Arrays.asList("*");
		return new ArrayList<>(Arrays.asList(new PhysicalFlow(new PhysicalCompartment(from.getLabels()),
				new PhysicalCompartment(to.getLabels()), equation, requiredOperators)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BatchRateContactFlowPackage.Literals.BATCH;
	}
} //BatchImpl
