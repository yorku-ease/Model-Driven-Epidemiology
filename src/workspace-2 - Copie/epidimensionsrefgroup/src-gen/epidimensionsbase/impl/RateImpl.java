/**
 */
package epidimensionsbase.impl;

import epidimensionsbase.EpidimensionsbasePackage;
import epidimensionsbase.FlowVisitor;
import epidimensionsbase.Rate;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rate</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class RateImpl extends FlowImpl implements Rate {

	@Override
	public void accept(Object visitor) throws Exception {
		((FlowVisitor<?>) visitor).visit(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsbasePackage.Literals.RATE;
	}

} //RateImpl
