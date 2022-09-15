/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.EpimodelPackage;
import epimodel.Flow;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epimodel.impl.CompartmentImpl#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompartmentImpl extends MinimalEObjectImpl.Container implements Compartment {
	
	@Override
	public void edit(Shell shell, List<Control> controls) {
		if (getClass() != CompartmentImpl.class)
			throw new RuntimeException();
	}
	
	@Override
	public void create(EObject dom, Shell shell, List<Control> controls) {
		shell.setText("Create Compartment " + getClass().toString());
        shell.setLayout(new GridLayout(2, false));
        epimodel.util.Edit.addText(shell, controls, "Labels (comma sparated): ");
        Text t = new Text(shell, SWT.NONE);
		t.setText("");
		t.setLayoutData(new GridData(300, 50));
		controls.add(t);
        epimodel.util.Edit.addText(shell, controls, "");
        epimodel.util.Edit.addBtn(shell, controls, "Create", () -> {
        	epimodel.util.Edit.transact(dom, () -> {
        		String labelsCSV = t.getText();
        		for (String label: labelsCSV.split(","))
        			getLabel().add(label.trim());
        	});
        	shell.close();
        });
	}

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected EList<String> label;

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		return new ArrayList<>(Arrays.asList(new PhysicalCompartment(getLabel())));
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		return getPhysicalCompartments();
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		return getPhysicalCompartments();
	}

	@Override
	public List<Flow> getFlows() {
		return new ArrayList<>();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpimodelPackage.Literals.COMPARTMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getLabel() {
		if (label == null) {
			label = new EDataTypeUniqueEList<String>(String.class, this, EpimodelPackage.COMPARTMENT__LABEL);
		}
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT__LABEL:
			return getLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT__LABEL:
			getLabel().clear();
			getLabel().addAll((Collection<? extends String>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT__LABEL:
			getLabel().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT__LABEL:
			return label != null && !label.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}
} //CompartmentImpl
