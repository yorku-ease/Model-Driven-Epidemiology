/**
 */
package couplingFlow.provider;

import couplingFlow.CouplingFlow;
import couplingFlow.CouplingFlowPackage;

import epimodel.provider.FlowItemProvider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * This is the item provider adapter for a {@link couplingFlow.CouplingFlow} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CouplingFlowItemProvider extends FlowItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CouplingFlowItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addFirstFromPropertyDescriptor(object);
			addSecondFromPropertyDescriptor(object);
			addSecondToPropertyDescriptor(object);
			addFirstToPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the First From feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFirstFromPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_CouplingFlow_firstFrom_feature"),
						getString("_UI_PropertyDescriptor_description", "_UI_CouplingFlow_firstFrom_feature",
								"_UI_CouplingFlow_type"),
						CouplingFlowPackage.Literals.COUPLING_FLOW__FIRST_FROM, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Second From feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondFromPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_CouplingFlow_secondFrom_feature"),
						getString("_UI_PropertyDescriptor_description", "_UI_CouplingFlow_secondFrom_feature",
								"_UI_CouplingFlow_type"),
						CouplingFlowPackage.Literals.COUPLING_FLOW__SECOND_FROM, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Second To feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondToPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_CouplingFlow_secondTo_feature"),
						getString("_UI_PropertyDescriptor_description", "_UI_CouplingFlow_secondTo_feature",
								"_UI_CouplingFlow_type"),
						CouplingFlowPackage.Literals.COUPLING_FLOW__SECOND_TO, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the First To feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFirstToPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_CouplingFlow_firstTo_feature"),
						getString("_UI_PropertyDescriptor_description", "_UI_CouplingFlow_firstTo_feature",
								"_UI_CouplingFlow_type"),
						CouplingFlowPackage.Literals.COUPLING_FLOW__FIRST_TO, true, false, true, null, null, null));
	}

	/**
	 * This returns CouplingFlow.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/CouplingFlow"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((CouplingFlow) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_CouplingFlow_type")
				: getString("_UI_CouplingFlow_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
