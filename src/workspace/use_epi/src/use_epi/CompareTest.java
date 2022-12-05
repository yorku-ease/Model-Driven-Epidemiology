package use_epi;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//import java.util.Arrays;
//
//import org.junit.jupiter.api.Test;

class CompareTest {
//	@Test
//	void test1() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 0);
//		assertTrue(res.mappingsLeft.size() == 1);
//		assertTrue(res.mappingsRight.size() == 1);
//		assertTrue(res.additions.size() == 0);
//		assertTrue(res.substractions.size() == 0);
//		assertTrue(res.mappingsLeft.equals(res.mappingsRight));
//	}
//	
//	@Test
//	void test2() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 0);
//		assertTrue(res.mappingsLeft.size() == 1);
//		assertTrue(res.mappingsRight.size() == 1);
//		assertFalse(res.mappingsLeft.equals(res.mappingsRight));
//	}
//	
//	@Test
//	void test3() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 0);
//		assertTrue(res.mappingsLeft.size() == 1);
//		assertTrue(res.mappingsRight.size() == 1);
//		assertFalse(res.mappingsLeft.equals(res.mappingsRight));
//	}
//	
//	@Test
//	void test4() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "0")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 0);
//		assertTrue(res.mappingsLeft.size() == 0);
//		assertTrue(res.mappingsRight.size() == 0);
//		assertTrue(res.additions.size() == 1);
//		assertTrue(res.additions.get(0).equals(Arrays.asList("S", "1")));
//		assertTrue(res.substractions.size() == 1);
//		assertTrue(res.substractions.get(0).equals(Arrays.asList("S", "0")));
//	}
//	
//	@Test
//	void test5() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S"),
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//
//		System.out.println(res);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 1);
//		assertTrue(res.specializations.get(Arrays.asList("S")).size() == 2);
//		assertTrue(res.mappingsLeft.size() == 0);
//		assertTrue(res.mappingsRight.size() == 0);
//		assertTrue(res.additions.size() == 0);
//		assertTrue(res.substractions.size() == 0);
//	}
//	
//	@Test
//	void test6() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S"),
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//
//		System.out.println(res);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.generalizations.get(Arrays.asList("S")).size() == 1);
//		assertTrue(res.specializations.size() == 1);
//		assertTrue(res.mappingsLeft.size() == 0);
//		assertTrue(res.mappingsRight.size() == 0);
//		assertTrue(res.additions.size() == 0);
//		assertTrue(res.substractions.size() == 0);
//	}
}
