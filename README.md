# api-tests

## Running the tests using Maven
Navigate to project root directory in your terminal (pro tip: IntelliJ automatically starts new
Terminal tabs in project root directory). Write your command and hit enter. That's it. Now the
only thing left is to figure out which command to execute. First of, let's see what options are
at our disposal.

#### Pre-requisites:
Install the following:
* Java 11
* Maven
* Allure(if you need local report generation to work)

#### Profiles:
Certain circumstances under which tests will be executed are defined using Maven profiles. They
define testing environment, filter tests, choose number of threads to run on etc.
```bash
### Profiles
Profile             Description
run_tests           Needed in order to run tests

#### Variables:
Use variables to parametrise test execution as well, but they define different set of rules.

Variable            Default value       Description
thread.count         1                  Optional. Tests will be executed in specified number of threads.
test.groups          /                  Optional. Specifies which test suite will be executed, runs all test if left out
env                  dev                Optional. Tests will be run against different environments based on this variable
```

#### Examples
Let's go over a few examples:

###### Example 1:
```shell script
$ mvn clean test -Prun_tests
$ mvn clean test -Prun_tests -Dtest.groups=converter
$ mvn clean test -Prun_tests -Dtest.groups=converter,otherGroup
$ mvn clean test -Prun_tests -Dtest.groups=converter -Dthread.count=3
```

## Generating Allure report
After test execution using Maven, it's common to want to check test results in a more readable way.
For that we rely on Allure report tool, which relies on results generated by Maven Surefire plugin.
To generate Allure report, simply run the one of the following commands from project root directory
in your terminal:
For results generated in `api-framework` module:
```shell script
$ sh allure-generator
```

## Possible improvements for the API
* API always returns 200 OK, regardless of the input, change this to return 400 BAD_REQUEST for invalid data
* Add standardized error response to the API, possibly with a message describing the error, so the user can change the input based on the error
* We need docs for the API(swagger for example) that defines which date formats are expected for the query parameter that is used for conversion
* In case of timestamp > date string conversion, for the following examples: 1646143200 converts to "2022-03-01 02:00:00", 1646100000 converts to "2022-03-01 02:00:00". We need a 24h format in the API response or AM/PM to be added in order to know what exact time it is
* UI client does not work on GMT zone, rather it does the conversion based on the zone of the client browser, we can have both results be shown instead
