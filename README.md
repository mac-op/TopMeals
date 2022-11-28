# CMPUT301Project
test
Add the progress report here 

https://docs.google.com/document/d/1lh4SNRUeVa-xd2OGM1ffxn5c1d9eVU4vwmZBXG-Fn7Y/edit?usp=sharing

## Project Part Two Feedback

> Tools Use
    >> ⚠  fair Contributions to GitHub by teams ..50 commits, no issues
    >> ⚠ Fair use of GitHub features like branching 
> Object Oriented Analysis
    >>Ingredient can not create a new ingredient rather it stores information, ingredientStorage is creating and deleting a new ingredient. It would be better to add the edit functionality within the class that is creating and deleting an ingredient if there is no additional checks or logics to take care when editing. 
    >> Same comments for the recipe classes, one other thing that why modifyrecipe is adding and deleting a new recipe shouldn't it be responsibility of recipebook. How can a mealplan delete itself, it would be better to create a class to maintain meal plans (like you have done for ingredients and recipes).
    >> Coupling managed: no majority of the classes of one module interact with the majority of classes of another module, reduce the dependence between classes of different modules (you can consider at least three modules like ingredient, recipe, mealplan)




##Project Part3 Rubrics

Addressing Feedback
•    All well addressed
•    Issues tracked

Code Base of Prototype
•    Excellent quality
•    At least ½ of requirements implemented and fully done
•    Comprehensive connectivity to server
•    Clear and readable code with useful comments
•    Follows conventions
•    Checks for errors

Code Documentation
•    Third party could easily understand
•    Complete and correct Javadoc comments for entity (model) classes and methods
•    Consistent with requirements, design, code, and tests

Test Cases
•    Test exist and can run
•    Convincing and passing testing for completed implementation
•    Comprehensive unit tests of entity (model) classes
•    Comprehensive intent tests for implemented user stories
•    Correct tests
•    Realistic test data
•    Consistent with requirements, design, code, and documentation

Object-Oriented Design
•    Proper OO design with clear design intent
•    Separation of concerns and/or layering
•    Encapsulation and information hiding
•    Clear classes and interfaces
•    Key elements described
•    Correct notation
•    Neatly laid out and labeled diagrams
•    Helpful explanations or commentary
•    Consistent with requirements, code, tests, and documentation


Sprint Planning and Reviews
• Comprehensive
• Displays understanding of Scrum
• Displays regular and frequent pacing of working software
• Each sprint is planned by user story
• Riskier requirements are done earlier
• Each sprint is reviewed
• Members all present at each review
• User stories are fully “done done” (implemented, tested, integrated, documented)
• Early and frequent integration
• Continuous integration actions


Tangible Demo
• Demo ability
• Clear and coherent
• Logically organized by tangible features
• Realistic data and inputs
• All members present
• Members well coordinated

Code Base of Prototype

• Excellent quality
• At least ½ of requirements implemented and fully done
• Comprehensive connectivity to server
• Code is clear
• Readable
• Useful comments
• Checks for errors
• Follows conventions

Relative Quality

• Well above average relative quality, overall comprehensiveness, creativity, attractiveness, and innovation

# Project Part 3 Feedback

## Addressing Feedback

3 = Almost all were addressed

## User Interface Mockup and Storyboard

3 = Consistent and clear
• Complete UI mockups
• Labeled elements on UI mockups
• Detailed storyboard
• Labeled actions for storyboard transitions
• Covers all requirements
• Intuitive user interface
• Displays understanding of UI mockups and storyboarding

## Sprint Planning

3 =• Comprehensive
• Displays understanding of Scrum
• Displays regular and frequent pacing of working software
• Each sprint is planned by user story
• Some Riskier requirements are done earlier but some are not
• Members all present at each review
• Early and frequent integration using Github Actions.


## Tangible Demo
2 = • Fair Demo ability
• Clear and coherent
• Logically organized by tangible features
• Realistic data and inputs
• All members present except one was sick
• Members well coordinated

## Tools Practices
3 = • All team members contribute to GitHub regularly
• Effective use of GitHub features 
Comments:
GitHub projects should be used.
One member has low contribution according to the GitHub contributors

## Relative Quality
3 = good relative quality as app was personalized like a portal

## Code Base

2 = • Approximately ½ of requirements implemented and fully done
• Connectivity to server
• Readable

- Code convention are not followed in all classe.
- One classe have their name start with small case. 
- Error check and validation is missing in some classes.



## Code Documentation

2 = - No unit tests are written to cover the entity 
classes.
- Intent tests does not cover the all UI components.
- Commented out code segments should be deleted from the main branch.
- Code comments and javadoc comments are not sufficient for some entity classes
- Many methods have javadoc comments but no description of the parameters and  return type.

## Product Backlog

0 = Cannot view the backlog, it is not accessible in GitHub's "Project" tab, nor is it available in the "Issues" section

## Test Cases

1 = ✓ Comprehensive intent tests for implemented user stories
✓ Correct tests wtih realistic data
✘ No unit tests of entity (model) classes implemented
✘ All intent tests fail due to usage of inexistent layout IDs in the Login class

## Object Oriented Design
1 = why recipe is not connected with recipebook, does recipebook don't have any recipes. Similar to this many connections are missing. uml only shows inheritance, no link showing aggregation or composition. many classes or set of classes are standalone. only appcompactactivity has any relation to most of the classes does this mean that every interaction between classes is done through appcompactactivity. uml and crc cards don't match up, for example crc is saying ingredientstorage is a collaborator of ingredient but uml has nothing to show for this.

Total = 8.11

## NB.. These values are not final

## Demo Rubric

## Demo Rubric

Demo ability / Polished / Clear and coherent / Motivated / Compelling, holds attention, tells a story / Coverage of features logically organized by customer usage / Well explained user interface / Easy to follow user roles / Realistic data and inputs / All members present / Team well coordinated in demo / Within time limit / No misspellings / Clear vocals from everyone / Eye contact, rarely consulting or reading notes / Involves the audience / Good and concise responses to questions