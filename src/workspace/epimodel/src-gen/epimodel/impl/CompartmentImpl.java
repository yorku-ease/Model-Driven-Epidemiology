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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import java.util.Map;

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

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected EList<String> label;

	
	public boolean isDivided() {
		
		Compartment comp = this;
		while (comp.getChildrens().size()!=0) {
	
			if (comp.eContents().size() > 1)
				return true;
			else if (comp.eContents().size() == 1) {
			
				comp = comp.getChildrens().get(0);
			
			}
			else 
				return false;
		}
		return false;
	}
	
	public Compartment getParent() {
		if(this.eContainer.eContainer() instanceof Compartment)
			return (Compartment)this.eContainer.eContainer();
		else 
			return null;
	}
	/*
	 Gives the label of the actual compartment
	 Example : [SEIR, I, Group a] -> Group a.
	 */
	public String getSimpleCompartmentLabel() {
		
		return this.getLabel().get(this.getLabel().size()-1);
	}
	
	public List<Compartment> getChildrens(){
		List <Compartment> lcomp = new ArrayList<>();
	//	System.out.println(this.eContents());
		if(!this.eContents().isEmpty()){
			for (EObject wrap : this.eContents()) {
				for (EObject object : wrap.eContents()) {
					if (object instanceof Compartment) {
						Compartment comp = (Compartment) object;
						lcomp.add(comp);
					}
				}
			}
			//System.out.println("LISTE FAITE  " + lcomp);
			//System.out.println("ATTENDU  " + this.eContents().get(0).eContents());
		}
	
		return lcomp;
	}
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
	/*
	@Override
	public Map<String, List<Compartment>> getAllBranches(){
		
		HashMap<String, List<Compartment>> br = new HashMap<>();
		ArrayList<Compartment> cp = new ArrayList<>();
		cp.add(this);
		br.put(this.label.get(1	),cp);
			
		
		return br;
	}
	*/
	
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
