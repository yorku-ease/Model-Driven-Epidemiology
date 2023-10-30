/**
 */
package linkcompartment.impl;

import epimodel.Compartment;
import epimodel.impl.CompartmentImpl;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import linkcompartment.Link;
import linkcompartment.LinkcompartmentPackage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link linkcompartment.impl.LinkImpl#getLink <em>Link</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LinkImpl extends CompartmentImpl implements Link {

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		List<PhysicalCompartment> res = new ArrayList<>();
		Compartment ref = getLink();
		if (ref.eIsProxy())
			// see ref.eStorage private member for uri
			throw new RuntimeException("Unable to resolve reference");
		for (PhysicalCompartment pc : ref.getPhysicalCompartments()) {
			res.add(prependSelf(pc));
		}
		return res;
	}

	PhysicalCompartment prependSelf(PhysicalCompartment pc) {
		List<String> labels = new ArrayList<>(pc.labels);
		labels.addAll(0, getLabels());
		return new PhysicalCompartment(labels);
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		List<PhysicalCompartment> res = new ArrayList<>();
		Compartment ref = getLink();
		if (ref.eIsProxy())
			// see ref.eStorage private member for uri
			throw new RuntimeException("Unable to resolve reference");
		for (PhysicalCompartment pc : ref.getSources()) {
			res.add(prependSelf(pc));
		}
		return res;
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		List<PhysicalCompartment> res = new ArrayList<>();
		Compartment ref = getLink();
		if (ref.eIsProxy())
			// see ref.eStorage private member for uri
			throw new RuntimeException("Unable to resolve reference");
		for (PhysicalCompartment pc : ref.getSinks()) {
			res.add(prependSelf(pc));
		}
		return res;
	}

	@Override
	public List<PhysicalFlow> getEquations() {
		List<PhysicalFlow> res = new ArrayList<>();
		Compartment ref = getLink();
		if (ref.eIsProxy())
			// see ref.eStorage private member for uri
			throw new RuntimeException("Unable to resolve reference");
		for (PhysicalFlow eq : ref.getEquations())
			res.add(new PhysicalFlow(prependSelf(eq.source), prependSelf(eq.sink), eq.equation, eq.name));
		return res;
	}

	@Override
	public void edit(Shell shell, List<Control> controls) {
		shell.setText("Edit Link " + getLabel());
		shell.setLayout(new GridLayout(1, false));
		epimodel.util.Edit.addBtn(shell, controls, "Modify Labels", () -> {
			controls.forEach(Control::dispose);
			controls.clear();
			super.edit(shell, controls); // labels window
			shell.pack(true);
		});
	}

	/**
	 * The cached value of the '{@link #getLink() <em>Link</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLink()
	 * @generated
	 * @ordered
	 */
	protected Compartment link;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LinkcompartmentPackage.Literals.LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment getLink() {
		if (link != null && link.eIsProxy()) {
			InternalEObject oldLink = (InternalEObject) link;
			link = (Compartment) eResolveProxy(oldLink);
			if (link != oldLink) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LinkcompartmentPackage.LINK__LINK,
							oldLink, link));
			}
		}
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compartment basicGetLink() {
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLink(Compartment newLink) {
		Compartment oldLink = link;
		link = newLink;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LinkcompartmentPackage.LINK__LINK, oldLink, link));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LinkcompartmentPackage.LINK__LINK:
			if (resolve)
				return getLink();
			return basicGetLink();
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
		case LinkcompartmentPackage.LINK__LINK:
			setLink((Compartment) newValue);
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
		case LinkcompartmentPackage.LINK__LINK:
			setLink((Compartment) null);
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
		case LinkcompartmentPackage.LINK__LINK:
			return link != null;
		}
		return super.eIsSet(featureID);
	}

} //LinkImpl
