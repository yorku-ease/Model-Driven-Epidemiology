/**
 */
package epimodelgroup.util;

import epimodelgroup.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see epimodelgroup.EpimodelgroupPackage
 * @generated
 */
public class EpimodelgroupSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EpimodelgroupPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpimodelgroupSwitch() {
		if (modelPackage == null) {
			modelPackage = EpimodelgroupPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case EpimodelgroupPackage.EPIDEMIC: {
			Epidemic epidemic = (Epidemic) theEObject;
			T result = caseEpidemic(epidemic);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EpimodelgroupPackage.IDIMENSION: {
			IDimension iDimension = (IDimension) theEObject;
			T result = caseIDimension(iDimension);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EpimodelgroupPackage.DIMENSION: {
			Dimension dimension = (Dimension) theEObject;
			T result = caseDimension(dimension);
			if (result == null)
				result = caseIDimension(dimension);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EpimodelgroupPackage.META_COMPARTMENT: {
			MetaCompartment metaCompartment = (MetaCompartment) theEObject;
			T result = caseMetaCompartment(metaCompartment);
			if (result == null)
				result = caseIDimension(metaCompartment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EpimodelgroupPackage.META_FLOW: {
			MetaFlow metaFlow = (MetaFlow) theEObject;
			T result = caseMetaFlow(metaFlow);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EpimodelgroupPackage.DIMENSION_REF: {
			DimensionRef dimensionRef = (DimensionRef) theEObject;
			T result = caseDimensionRef(dimensionRef);
			if (result == null)
				result = caseIDimension(dimensionRef);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EpimodelgroupPackage.META_CONTACT: {
			MetaContact metaContact = (MetaContact) theEObject;
			T result = caseMetaContact(metaContact);
			if (result == null)
				result = caseMetaFlow(metaContact);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EpimodelgroupPackage.META_RATE: {
			MetaRate metaRate = (MetaRate) theEObject;
			T result = caseMetaRate(metaRate);
			if (result == null)
				result = caseMetaFlow(metaRate);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EpimodelgroupPackage.META_BATCH: {
			MetaBatch metaBatch = (MetaBatch) theEObject;
			T result = caseMetaBatch(metaBatch);
			if (result == null)
				result = caseMetaFlow(metaBatch);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Epidemic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Epidemic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEpidemic(Epidemic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDimension(IDimension object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDimension(Dimension object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Meta Compartment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Meta Compartment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetaCompartment(MetaCompartment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Meta Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Meta Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetaFlow(MetaFlow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dimension Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dimension Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDimensionRef(DimensionRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Meta Contact</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Meta Contact</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetaContact(MetaContact object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Meta Rate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Meta Rate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetaRate(MetaRate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Meta Batch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Meta Batch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetaBatch(MetaBatch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //EpimodelgroupSwitch
