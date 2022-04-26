/**
 */
package transformedEpidemic;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see transformedEpidemic.TransformedEpidemicPackage
 * @generated
 */
public interface TransformedEpidemicFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransformedEpidemicFactory eINSTANCE = transformedEpidemic.impl.TransformedEpidemicFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Transformed Epidemic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transformed Epidemic</em>'.
	 * @generated
	 */
	TransformedEpidemic createTransformedEpidemic();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TransformedEpidemicPackage getTransformedEpidemicPackage();

} //TransformedEpidemicFactory
