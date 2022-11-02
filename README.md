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