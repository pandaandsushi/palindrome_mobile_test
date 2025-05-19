# Palindrome x User Native Mobile Application
Native Android: Kotlin w/ XML View, min SDK is 21 and target SDK 34

## First Screen
- It has two inputTexts and two buttons.
- One inputText for name input and the other for input sentence text, to check whether the sentence is palindrome or not.
- A button with a “Check” title below the inputTexts
- Show as dialog with message “isPalindrome” if it’s palindrome and message “not   palindrome” if it’s not palindrome when clicking the button check
- And a button with a “Next” title below the Check Button.
- Go to the Second Screen when clicking the Next button.

## Second Screen
- It has a static “Welcome” text label/textview
- And it has two dynamic labels / textviews for the show name from the first screen and the other one is the Selected User Name label.
- It has a button “Choose a User”.
- Action click button “Choose a User” for goto third screen.

## Third Screen
- It displays a List/Table view of Users.  
- Data is collected from the API at [reqres.in](https://reqres.in), showing `email`, `first_name`, `last_name`, and `avatar`.  
- Supports pull-to-refresh and automatic loading of the next page when scrolling to the bottom.  
- If no data is available, an empty state should be displayed.  
- Use `page` and `per_page` query parameters to fetch paginated data from the API.  
- When a user is clicked in the list, their name should update the “Selected User Name” label on the Second Screen without navigating to a new screen (stay on the current screen).

by: Thea Josephine Halim