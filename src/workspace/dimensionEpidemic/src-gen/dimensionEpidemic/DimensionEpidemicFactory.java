/**
 */
package dimensionEpidemic;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see dimensionEpidemic.DimensionEpidemicPackage
 * @generated
 */
public interface DimensionEpidemicFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DimensionEpidemicFactory eINSTANCE = dimensionEpidemic.impl.DimensionEpidemicFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Dimension Epidemic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dimension Epidemic</em>'.
	 * @generated
	 */
	DimensionEpidemic createDimensionEpidemic();

	/**
	 * Returns a new object of class '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dimension</em>'.
	 * @generated
	 */
	Dimension createDimension();

	/**
	 * Returns a new object of class '<em>Dimension Wrapper</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dimension Wrapper</em>'.
	 * @generated
	 */
	DimensionWrapper createDimensionWrapper();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DimensionEpidemicPackage getDimensionEpidemicPackage();

} //DimensionEpidemicFactory
