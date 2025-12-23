package seirmodel.compare;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import seirmodel.SEIRModel;
import seirmodel.SeirmodelPackage;

public class CompareRunner {

    public static void main(String[] args) {
        System.out.println("=== SEIR Model Comparison Tool ===");
        System.out.println("Enter filenames of model 1 and 2 <x.seirmodel y.seirmodel> :");

        Scanner sc = new Scanner(System.in);
        String[] tokens = sc.nextLine().trim().split("\\s+");
        sc.close();

        if (tokens.length != 2) {
            System.err.println("Expected two .seirmodel filenames.");
            return;
        }

        String model1File = tokens[0];
        String model2File = tokens[1];

        validateSeirModel(model1File);
        validateSeirModel(model2File);

        SEIRModel model1 = load(model1File);
        SEIRModel model2 = load(model2File);

        System.out.println();
        System.out.println("Comparing models:");
        System.out.println("  Model 1 = " + new File(model1File).getName());
        System.out.println("  Model 2 = " + new File(model2File).getName());
        System.out.println();

        SeirModelComparator comparator =
                new SeirModelComparator(
                        new File(model1File).getName(),
                        new File(model2File).getName()
                );

        List<ModelDifference> diffs = comparator.compare(model1, model2);
        DiffPrinter.print(diffs);
    }

    private static void validateSeirModel(String path) {
        if (!path.endsWith(".seirmodel")) {
            throw new IllegalArgumentException(
                    "Only .seirmodel files are supported: " + path
            );
        }

        File f = new File(path);
        if (!f.exists()) {
            throw new IllegalArgumentException(
                    "Model file not found (relative to working directory): " + f.getAbsolutePath()
            );
        }
    }

    private static SEIRModel load(String path) {
        ResourceSet rs = new ResourceSetImpl();

        // Register factory + package (THIS is critical and correct)
        rs.getResourceFactoryRegistry()
          .getExtensionToFactoryMap()
          .put("seirmodel", new XMIResourceFactoryImpl());

        rs.getPackageRegistry()
          .put(SeirmodelPackage.eNS_URI, SeirmodelPackage.eINSTANCE);

        URI uri = URI.createFileURI(new File(path).getAbsolutePath());
        Resource res = rs.getResource(uri, true);

        return (SEIRModel) res.getContents().get(0);
    }
}
