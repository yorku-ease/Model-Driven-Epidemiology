/**
 */
package epimodelgroup;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see epimodelgroup.EpimodelgroupPackage
 * @generated
 */
public interface EpimodelgroupFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpimodelgroupFactory eINSTANCE = epimodelgroup.impl.EpimodelgroupFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Epidemic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Epidemic</em>'.
	 * @generated
	 */
	Epidemic createEpidemic();

	/**
	 * Returns a new object of class '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dimension</em>'.
	 * @generated
	 */
	Dimension createDimension();

	/**
	 * Returns a new object of class '<em>Meta Compartment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Meta Compartment</em>'.
	 * @generated
	 */
	MetaCompartment createMetaCompartment();

	/**
	 * Returns a new object of class '<em>Dimension Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dimension Ref</em>'.
	 * @generated
	 */
	DimensionRef createDimensionRef();

	/**
	 * Returns a new object of class '<em>Meta Contact</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Meta Contact</em>'.
	 * @generated
	 */
	MetaContact createMetaContact();

	/**
	 * Returns a new object of class '<em>Meta Rate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Meta Rate</em>'.
	 * @generated
	 */
	MetaRate createMetaRate();

	/**
	 * Returns a new object of class '<em>Meta Batch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Meta Batch</em>'.
	 * @generated
	 */
	MetaBatch createMetaBatch();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EpimodelgroupPackage getEpimodelgroupPackage();

} //EpimodelgroupFactory
