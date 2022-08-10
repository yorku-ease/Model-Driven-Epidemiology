/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.Epidemic;
import epimodel.EpimodelPackage;
import epimodel.util.PhysicalCompartment;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

	public Map<String, List<Compartment>> getAllBranches(){
		
		HashMap<String, List<Compartment>> br = new HashMap<>();
		ArrayList<Compartment> epi = new ArrayList<>();
		ArrayList<Compartment> total = new ArrayList<>();
		
		TreeIterator<EObject> allContents = this.eAllContents();
		Compartment tmp = null;
		while (allContents.hasNext()) {
			EObject object = allContents.next();
			if (object instanceof Compartment) {
				Compartment comp = (Compartment)object;
				total.add(comp);
				if (tmp == null) {
					//System.out.println("\nC'EST LE DEBUT  on ajoute : " + comp);
					epi.add(comp);
				}	
				else if (comp.getLabel().containsAll(tmp.getLabel())) {
					//System.out.println("\nCONTINUITE DE LA BRANCHE : " + "AVEC nouveaux = "+ comp + "et ancien = "+ tmp);
					ArrayList<Compartment> epi_tmp = new ArrayList(epi);
					epi = new ArrayList<>(epi_tmp);
					epi.add(comp);
				}
				else if (!comp.getLabel().containsAll(tmp.getLabel())) {
				//	System.out.println("\nNOUVELLE SOUS BRANCHE QUI FINIT PAR   "+ comp );
					epi = new ArrayList<>();
					for (Compartment c : total) {
					//	System.out.println("\nREALIMENTATION DE LA SOUS BRANCHE AVEC : ");
						if (comp.getLabel().containsAll(c.getLabel()) && !comp.equals(c)) {
						//	System.out.println("\nCOMP " + c );
							epi.add(c);
						}
					}
					epi.add(comp);
				}
				else 
					epi.add(comp);
			
			
				String key = null;
			
				for(int i = 0; i < comp.getLabel().size(); i++) {
					
					key = comp.getLabel().get(i);
				}
			 	br.put(key, epi);	
			 	tmp = comp;
			}

		    
		   
		}
		
		return br;
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
