/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.Epidemic;
import epimodel.EpimodelPackage;
import epimodel.util.PhysicalCompartment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epimodel.impl.EpidemicImpl#getId <em>Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class EpidemicImpl extends MinimalEObjectImpl.Container implements Epidemic {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EpidemicImpl() {
		super();
	}

	
	
	/*
	 Display and returns an associative array of all the Compartment of the differents branchs of a given epimodel.
	 The key is every Compartment node of the model and returs a list of the node's branch without the node's children
	 */
	public Map<String, List<Compartment>>  getAllCompartmentBranches(){
		
		
		Map<String, List<Compartment>> br = new HashMap<>();
		
		for(List<EObject> branche : getAllBranches()) {
			for (EObject node : branche) {
				
				if(node instanceof Compartment) {
					Compartment actualComp =(Compartment) node;
					List<Compartment> lcmp= new ArrayList<>();
					for(int i =0; i<= branche.indexOf(node); i++) { //Getting back all the upper hierarchy of the node
						if(branche.get(i) instanceof Compartment) {
							Compartment comp = (Compartment) branche.get(i);
							lcmp.add(comp);
						}
					}
					
					if(!br.containsKey(actualComp.getSimpleCompartmentLabel())) { //Keeping unicity of a key
						br.put(actualComp.getSimpleCompartmentLabel(), lcmp);
					}
				}
			}
		}
	
		return br;
	}
	
	public Map<String, Compartment>  getModelTree(){
		
		
		Map<String, Compartment> br = new HashMap<>();
		EObject object = null;
		TreeIterator<EObject> allContents = this.eAllContents();
	
		
		while (allContents.hasNext()) {
			
			object = allContents.next();
		
			if(object instanceof Compartment) {
				Compartment comp = (Compartment) object;
				if(!br.containsKey(comp.getSimpleCompartmentLabel())) {
					br.put(comp.getSimpleCompartmentLabel(), comp);

				}
				
			}
			
		}
		return br;
	}
	
	/*
	 Displays a tree vision of a give model.
	 */
	public void printModelTree() {
		System.out.println("DEBUT");
		EObject object = null;
		Compartment tmp = null;
		TreeIterator<EObject> allContents = this.eAllContents();
		String space = "   ";
	

		List <Compartment> pastComp = new ArrayList<>();
		Epidemic root = (Epidemic) allContents.next().eContainer();
		System.out.println(root.getId());
		while (allContents.hasNext()) {
			
			object = allContents.next();
			
			if(object instanceof Compartment) {
				Compartment comp = (Compartment) object;
				if(!pastComp.contains(comp)) {
					pastComp.add(comp);

					System.out.println(space.repeat(getOneBranch(object).size()) + comp.getLabel() );
				}
				
			}
			
		}
		System.out.println("FIN PRINT");
	}
	
	
	
	/*
	 Displays all the Compartment of an epimodel Branches with right indentation. 
	 
	 */
	
	public void printAllCompartmentBranches() {
		String space = "";
		for(List<EObject> branche : getAllBranches()) {
			System.out.println("\n BRANCHE   :   ");
			space = "";
			for (EObject node : branche) {
				
				if(node instanceof Compartment) {
					Compartment comp = (Compartment) node;
					System.out.println(space+ comp.getLabel());
					space += "     ";
				}
			}
		}
	}
	
	/*
	 Displays all Branches with right indentation. 
	 
	 */
	public void printAllBranches() {
		
		String space = "";
		for(List<EObject> branche : getAllBranches()) {
			System.out.println("\n BRANCHE   :   ");
			space = "";
			for (EObject node : branche) {
				System.out.println(space+ node);
				space += "     ";
			}
		}
	}
	
	
	
	
	/*
	 Regroups all branches of the epi_model.
	 Returns a List of Lists representing branches
	 */
	public List <List<EObject>>  getAllBranches(){
		EObject object = null;
		TreeIterator<EObject> allContents = this.eAllContents();
		List <List<EObject>> allBranches = new ArrayList<>();
		while (allContents.hasNext()) {
			object = allContents.next();
			
			if(object.eContents().isEmpty()) { //Looking for the leaves
				allBranches.add(getOneBranch(object)); 
			}
		}
		
		
		return allBranches;
		
		
	}
	/*
	 Gets the branch associated to a given Leaf.
	returns a list of EObjects 
	  */
	 
	public List <EObject> getOneBranch(EObject Leaf){
		
		List <EObject> oneBranch = new ArrayList<>();
		do {
			oneBranch.add(Leaf);
			Leaf = Leaf.eContainer();
		}while (Leaf != null);
		
		Collections.reverse(oneBranch); // reversing the list elements to match the actual branch
		
		return oneBranch;
		
	}
	 
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpimodelPackage.Literals.EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpimodelPackage.EPIDEMIC__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpimodelPackage.EPIDEMIC__ID:
			return getId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EpimodelPackage.EPIDEMIC__ID:
			setId((String) newValue);
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
		case EpimodelPackage.EPIDEMIC__ID:
			setId(ID_EDEFAULT);
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
		case EpimodelPackage.EPIDEMIC__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //EpidemicImpl
