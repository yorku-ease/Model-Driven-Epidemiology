/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsrefgroup.Batch;
import epidimensionsrefgroup.EpidimensionsrefgroupPackage;

import org.eclipse.emf.ecore.EClass;

import epidimensionsbase.FlowVisitor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Batch</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BatchImpl extends FlowImpl implements Batch {

	@Override
	public void accept(Object visitor) throws Exception {
		((FlowVisitor<?>) visitor).visit((epidimensionsbase.Batch) this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsrefgroupPackage.Literals.BATCH;
	}

} //BatchImpl
