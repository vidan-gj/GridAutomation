package suite;

import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import unit.AddonsManipulationTesting;
import unit.AdminApplicationLinksTest;
import unit.AuthUserTest;
import unit.CheckLicenseExpyTest;
import unit.InactiveUsersMacroTest;
import unit.SpaceManipulationTest;
import unit.ToolsPageTest;
import unit.UserConfigTest;
import data.ExcelUtility;
import data.ReadPropertiesFile;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CheckLicenseExpyTest.class,
	AuthUserTest.class,
	ToolsPageTest.class,
	AddonsManipulationTesting.class,
	InactiveUsersMacroTest.class,
	AdminApplicationLinksTest.class,
	UserConfigTest.class
})
public class UnitTestSuite {

}
