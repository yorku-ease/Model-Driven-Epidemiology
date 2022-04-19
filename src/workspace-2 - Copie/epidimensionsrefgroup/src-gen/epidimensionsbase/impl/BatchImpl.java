/**
 */
package epidimensionsbase.impl;

import epidimensionsbase.Batch;
import epidimensionsbase.EpidimensionsbasePackage;
import epidimensionsbase.FlowVisitor;

import org.eclipse.emf.ecore.EClass;

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
		((FlowVisitor<?>) visitor).visit(this);
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
		return EpidimensionsbasePackage.Literals.BATCH;
	}

} //BatchImpl
