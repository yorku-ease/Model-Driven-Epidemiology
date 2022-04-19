/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsrefgroup.EpidimensionsrefgroupPackage;
import epidimensionsrefgroup.Rate;

import org.eclipse.emf.ecore.EClass;

import epidimensionsbase.FlowVisitor;

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
		((FlowVisitor<?>) visitor).visit((epidimensionsbase.Rate) this);
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
		return EpidimensionsrefgroupPackage.Literals.RATE;
	}

} //RateImpl
