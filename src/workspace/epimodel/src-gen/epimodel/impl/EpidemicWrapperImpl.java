/**
 */
package epimodel.impl;

import epimodel.Epidemic;
import epimodel.EpidemicWrapper;
import epimodel.EpimodelPackage;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic Wrapper</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epimodel.impl.EpidemicWrapperImpl#getEpidemic <em>Epidemic</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EpidemicWrapperImpl extends MinimalEObjectImpl.Container implements EpidemicWrapper {
	
	public void edit(EObject dom, Shell shell, List<Control> controls) {
		shell.setText("Select Epidemic Type");
        shell.setLayout(new GridLayout(1, false));
		if (getEpidemic() == null) {
	        List<EClass> epidemics = epimodel.util.Edit.getNonAbstractEClassesOfType(
	        		EpimodelPackage.Literals.EPIDEMIC);
	        for (EClass epiClass : epidemics)
	        	epimodel.util.Edit.addBtn(shell, controls, "Create " + epiClass.getName(), () -> {
        			controls.forEach(c -> c.dispose());
        			controls.clear();
        			epimodel.util.Edit.transact(dom, () -> setEpidemic((Epidemic) EcoreUtil.create(epiClass)));
        			getEpidemic().edit(dom, shell, controls);
        			shell.pack(true);
	        	});
		} else {
        	epimodel.util.Edit.addText(shell, controls, "Epidemic Already Exists");
        	epimodel.util.Edit.addBtn(shell, controls, "Close", () -> {
        		shell.close();
    		});
		}
	}
	
	/**
	 * The cached value of the '{@link #getEpidemic() <em>Epidemic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEpidemic()
	 * @generated
	 * @ordered
	 */
	protected Epidemic epidemic;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EpidemicWrapperImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpimodelPackage.Literals.EPIDEMIC_WRAPPER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Epidemic getEpidemic() {
		return epidemic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEpidemic(Epidemic newEpidemic, NotificationChain msgs) {
		Epidemic oldEpidemic = epidemic;
		epidemic = newEpidemic;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC, oldEpidemic, newEpidemic);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEpidemic(Epidemic newEpidemic) {
		if (newEpidemic != epidemic) {
			NotificationChain msgs = null;
			if (epidemic != null)
				msgs = ((InternalEObject) epidemic).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC, null, msgs);
			if (newEpidemic != null)
				msgs = ((InternalEObject) newEpidemic).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC, null, msgs);
			msgs = basicSetEpidemic(newEpidemic, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC,
					newEpidemic, newEpidemic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC:
			return basicSetEpidemic(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC:
			return getEpidemic();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC:
			setEpidemic((Epidemic) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC:
			setEpidemic((Epidemic) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case EpimodelPackage.EPIDEMIC_WRAPPER__EPIDEMIC:
			return epidemic != null;
		}
		return super.eIsSet(featureID);
	}

} //EpidemicWrapperImpl
