// Copyright (c) 2019 Travelex Ltd

package bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"}, glue = "bdd")
public class ComponentTestRunner {
}
