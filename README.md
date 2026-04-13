# In-Class Exercise: Defect Detection and Repair

## Technical Requirements

Before starting this exercise, ensure you have the following:

### Software

- **Java 11+** - verify with `java -version`
- **Maven** - verify with `mvn -version`
- **Git**
- Perl >= 5.0.12
- cpanm [see https://metacpan.org/pod/App::cpanminus](https://metacpan.org/pod/App::cpanminus)

### Skills

- Basic Java project structure understanding
- Basic Git branching and checkout operations
- Ability to interpret unit test failures

### Setup Verification

Run these commands to verify your setup:

```bash
java -version   # Should show Java 11 or higher
mvn -version    # Should show Maven is installed
git --version   # Should show Git is installed
```

---

## Overview

When tests fail, developers need to find the buggy code quickly. **Fault Localization (FL)** identifies suspicious lines. **Spectrum-Based Fault Localization (SBFL)** is a common way to do this where code lines are ranked based on suspiciousness scores derived from passing/failing test coverage. [Flacoco](https://github.com/ASSERT-KTH/flacoco) is an example of an SBFL tool ([paper link](https://openreview.net/forum?id=7rXjlQtFYg)). SBFL tools like Flacoco works in three steps:

1. **Execute all tests** and record which ones pass/fail
2. **Track code coverage** for each test (which lines each test executes)
3. **Compute suspiciousness scores** using a formula for example:
   - Lines executed by many failing tests but few passing tests -> HIGH suspiciousness
   - Lines executed by many passing tests but few failing tests -> LOW suspiciousness
   - Lines not executed by any test -> Not ranked

Developers inspect lines from highest to lowest suspiciousness until they find the bug. Flacoco uses the **Ochiai formula** (if interested, you can read more about it [here](https://ieeexplore.ieee.org/abstract/document/4041886)).

In this exercise, you will use Flacoco for FL in two stages:

1. **Demo Repository (Part 1):** a small demo repository where bug localization is straightforward. The code for this part is included in this repo.
2. **Real-World Defect4J bug (Part 2):** a real Defects4J bug from Apache Commons Lang where debugging is more complex.

---

## Repositories and Datasets for Investigation

This exercise uses two different codebases:

- **Part 1 demo repository (this repo), with the following branches**
  - `main`: all tests pass
  - `bug-1`: Calculator bug
  - `bug-2`: Bank bug

- **Part 2 real bug dataset:** **Defects4J** with Apache Commons Lang
  - Project: `Lang`
  - Bug ID: `33b` (buggy) and `33f` (fixed)

---

## GenAI Usage Policy

The use of Generative AI tools (e.g., ChatGPT, Cursor, GitHub Copilot, Claude) is permitted for this exercise with the following guidelines:

### Allowed Uses

- Understanding Flacoco options and output format
- Understanding Defects4J commands and setup errors
- Debugging environment/tooling issues
- Clarifying concepts (SBFL, suspiciousness, coverage-based ranking)

### Not Allowed

- Having AI generate your full report answers
- Submitting analysis you cannot explain yourself

### Requirements

- You must be able to explain all steps and conclusions in your own words
- Include a brief disclosure of AI support used in your submission (if any)

---

## Exercise Instructions

**Total Time: 75 minutes**

---

### Set Up Your Repository (5 minutes)

**Create your own repository from this template:**

1. Click **"Use this template"** button (green button at the top of the repo)
2. Select **"Create a new repository"**
3. Name it appropriately (e.g., `SAhandons-topic4-yourname`)

**Clone your repository:**

```bash
git clone <your-repo-url>
cd <repo-name>
```

> **Note:** Do NOT fork or clone this template directly. Always use the "Use this template" button to create your own copy.

---

Quickly inspect the code to understand what is in the repo:

- `src/main/java/calculator/Calculator.java`
- `src/test/java/calculator/CalculatorTest.java`
- `src/main/java/bank/Bank.java`
- `src/test/java/bank/BankTest.java`
- `pom.xml`

---

### Part 1: Controlled Fault Localization (25 minutes)

**Goal:** Learn the FL workflow on two small examples.

### Step 1: Use Flacoco with Calculator bug (`bug-1`)

#### Step 1.1. Checkout Buggy Branch and Run Tests

```bash
git checkout bug-1
mvn test
```

You should see **1 failing tests**.

- Take a screenshot of the test output with failures (**Screenshot 1**)
- Record: which function seems broken based on test names/errors

#### Step 1.2: Run Flacoco

Flacoco can be found at [https://github.com/ASSERT-KTH/flacoco](https://github.com/ASSERT-KTH/flacoco). For convenience, the Calculator repo already contains the pre-built JAR file that you can run.

```bash
java -jar flacoco-1.0.7-SNAPSHOT-jar-with-dependencies.jar 
```

You should get a list of `<class name>, <line number>, <suspiciousness score>`, with top-ranked lines shown first. 

- Take a screenshot of the top suspicious lines outputted by Flacoco (**Screenshot 2**).

#### Step 1.3: Investigate and Validate Suspicious Lines

Review the suspiciousness output (top-ranked lines appear first) to understand the bug. Use this output to help you identify and fix the bug(s) such that the tests pass again.

For your report, document:

- Which line(s) were actual buggy lines
- Suspiciousness score of the actual buggy line(s)
- Bug rank(s) (rank 1, rank 4 etc.)
- How did you fix the bug(s)
- Screenshot of passing tests for Calculator (**Screenshot 3**)

---

### Step 2: Run Flacoco on the Bank bug (`bug-2`)

Now apply the same workflow to a different bug in same demo repository (Note: test names intentionally don't follow best naming practices to allow you to think about the behavior of the test and code)

#### Step 2.1: Checkout Buggy Branch and Run Tests

```bash
git checkout bug-2
mvn test
```

- Take a screenshot of the test output with failures (**Screenshot 4**)
- Record: which function(s) seem broken based on failing test names/errors

#### Step 2.2: Run Flacoco

```bash
java -jar flacoco-1.0.7-SNAPSHOT-jar-with-dependencies.jar 
```

- Take a screenshot of Flacoco ranking for `bug-2` (**Screenshot 5**)

#### Step 2.3: Investigate and Validate Suspicious Lines

Use flacoco output to identify and fix the bug(s) until the tests pass.

Review the suspiciousness output (top-ranked lines appear first) to understand the bug. Use this output to help you identify and fix the bug(s) such that the tests pass again.

For your report, document:

- Which line(s) were actual buggy lines
- Suspiciousness score of the actual buggy line(s)
- Bug rank(s) (rank 1, rank 4 etc.)
- How did you fix the bug(s)
- Screenshot of passing tests (**Screenshot 6**)

---

### Part 2: Real-World Fault Localization with Defects4J (40 minutes)

**Goal:** Apply the same FL workflow to a production bug in Apache Commons Lang.

Make sure you have all the pre-requsities mentioned at the beginning of this README file for this to work!

#### Step 3.1: Setup the Defects4J Benchmark (this step takes ~5-7min)

```bash
cd ..
git clone git@github.com:snadi-teaching/defects4j.git
cd defects4j
./init.sh
export PATH=$PATH:"$(pwd)"/framework/bin
defects4j info -p Lang
```

If you get an error related to `String::Interpolate`, try the following:

```
cpanm --local-lib=~/perl5 local::lib && eval $(perl -I ~/perl5/lib/perl5/ -Mlocal::lib)
cpanm String::Interpolate
./init.sh
defects4j info -p Lang
```

If you are running on windows, you may need to slightly change the arguments passed to the defects4j command. Please check the README [https://github.com/snadi-teaching/defects4j](https://github.com/snadi-teaching/defects4j)

#### Step 3.3 Checkout Bug Lang-33b and run its tests

We will work with bug 33 in the apache-lang project. If you are curious about this bug, you can find more info about it in the original bug report [https://issues.apache.org/jira/browse/LANG-587](https://issues.apache.org/jira/browse/LANG-587)

You will first ask defects4j to get the exact code at the buggy version:

```bash
defects4j checkout -p Lang -v 33b -w lang-33-buggy
```

This will create a directory called `lang-33-buggy` with the buggy version of the code. You can always open this code in an IDE to browse it.

Run the tests:

```bash
cd lang-33-buggy
defects4j test
```

- How many failing tests do you see? Take a screenshot of the test results (**Screenshot 7**)

#### Step 3.3: Run Flacoco

You should still be in the `lang-33-buggy` folder to run this:

```bash
java -jar <path to the flacoco jar>
```

Enter the path to the flacoco jar you previously used. For example `java -jar /snadi/Downloads/SA-DEFECTDETECT-HANDSON/flacoco-1.0.7-SNAPSHOT-jar-with-dependencies.jar`. The exact path depends on where you cloned this repo before. Alternatively, copy the jar to the defects4j folder and use that.

- Take a screenshot of the flacoco output to show the suspicious lines (**Screenshot 7**)

#### Step 2.4: Compare Ranked Lines with Developer Fix

Open the code in `lang-33-buggy` in an IDE for easier navigation. 
Identify the ranked lines in the source code.

Defects4J allows you to check out the fixed version of bug. We can then diff the buggy and fixed versions to identify which line(s) the real developers changed to fix the bugs.

```bash
cd ..
defects4j checkout -p Lang -v 33f -w lang-33-fixed
diff lang-33-buggy/src/main/java/org/apache/commons/lang3/ClassUtils.java \
     lang-33-fixed/src/main/java/org/apache/commons/lang3/ClassUtils.java
```

This diff provides you the exact change the developer did. Given Flacoco's ranked lines and the developer changes, report:

- Which line number in the source file was the actual buggy line that the developer fixed? Include a screenshot of the code (**Screenshot 8**)
- Which rank was this line number at in Flacoco's ranked lines?
- Flacoco also flagged other lines with high suspiciousness score, but the developer only had to change one line to fix the bug. Discuss any thoughts you have about this.

---

## Submission Instructions

Submit the following to **Brightspace**:

### Required Submissions

**Total Points: 35 points**

1. **Part 1 (10 points)**
   - Correct bug explanation
   - Suspiciousness score and rank
   - Include required documentations
   - Include required screenshots

2. **Part 2 (25 points)**
   - Correct bug explanation
   - Suspiciousness score and rank
   - Include required documentations
   - Include required screenshots

### Submission Format

- Submit one combined PDF containing:
  - screenshots
  - analysis and reflections
  - comparison table
- File naming suggestion: `LastName_FirstName_SA_Topic3Handson.pdf`
- Include brief **GenAI Disclosure** if applicable

---

## Resources

- [Flacoco GitHub](https://github.com/ASSERT-KTH/flacoco)
- [Flacoco Wiki](https://github.com/ASSERT-KTH/flacoco/wiki)
- [Defects4J GitHub](https://github.com/rjust/defects4j)
- [FLACOCO paper](https://openreview.net/forum?id=7rXjlQtFYg)

---

## Acknowledgements

This exercise was developed with assistance from [Claude](https://claude.ai/)and [Cursor](https://cursor.com/). These tools were used to:

- Brainstorm ideas for the exercise structure and tasks
- Draft and refine this README documentation

---

## License

MIT License - See [LICENSE](LICENSE) file for details.
