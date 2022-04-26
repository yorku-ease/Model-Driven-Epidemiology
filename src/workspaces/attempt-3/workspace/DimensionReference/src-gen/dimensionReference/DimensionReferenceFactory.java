/**
 */
package dimensionReference;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see dimensionReference.DimensionReferencePackage
 * @generated
 */
public interface DimensionReferenceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DimensionReferenceFactory eINSTANCE = dimensionReference.impl.DimensionReferenceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Dimension Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dimension Link</em>'.
	 * @generated
	 */
	DimensionLink createDimensionLink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DimensionReferencePackage getDimensionReferencePackage();

} //DimensionReferenceFactory
