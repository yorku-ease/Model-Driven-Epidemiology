/**
 */
package epidimensionsrefgroup;

import epidimensionsbase.Compartment;
import epidimensionsbase.Dimension;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IDimension</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getIDimension()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IDimension extends Dimension, Compartment {
	
	void accept(IDimensionVisitor<?> v);
} // IDimension
