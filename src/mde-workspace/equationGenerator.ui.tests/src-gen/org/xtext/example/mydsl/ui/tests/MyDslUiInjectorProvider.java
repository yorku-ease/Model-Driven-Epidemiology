/*
 * generated by Xtext 2.32.0
 */
package org.xtext.example.mydsl.ui.tests;

import com.google.inject.Injector;
import equationGenerator.ui.internal.EquationGeneratorActivator;
import org.eclipse.xtext.testing.IInjectorProvider;

public class MyDslUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return EquationGeneratorActivator.getInstance().getInjector("org.xtext.example.mydsl.MyDsl");
	}

}
