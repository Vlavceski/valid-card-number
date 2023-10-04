# LevelUp take-home assignment - October 2023

This task is designed to help us evaluate your skills as a beginner programmer. The purpose of this task is NOT to get a 100% correct implementation - as that is almost impossible for the given problem :) - but rather to showcase what you already know.

The task is designed to take you a few hours to complete at most, and we will use your solutions as a talking point during our interview.
Feel free to make the solutions as fancy as you want, but whatever you do please don’t feel pressured to spend too much time on this task. A minimalist-looking version is completely fine for us - at least for now :)

Please use git during your development, and send us the solutions as a link to your git repository containing the solution for the task. You can use whichever code repository you want (i.e. `github`, `gitlab`, `bitbucket`...)

As a final note, we care a lot about being able to easily review and run your solution. Please take care to make
the project easy to run from a clean checkout and include at least the basic steps how to run it.

## The task

Let’s imagine you’re creating an e-commerce website. The site is looking good and you are ready to implement payments. The first step of your payment process is entering payment information. One of the possibilities is to pay with a credit card. You use an external payment system to process the payments, and the external system has a limited number of payments it can process.

To reduce the strain on the external payment system (caused by incorrect credit card details being entered), you decide to implement a second validation system which runs on your server and is not limited. This system is intended to perform simple sanity checks of the credit card information and respond with either a success or failure (more details below). The system is split into two parts, Frontend and Backend, and you will be making both of them.

**Your task is to implement a simple page with an input form to take in credit card information and send it to a Backend API for validation (that you will also implement).**

The Backend API should respond with either success or failure, and you should react appropriately in the Frontend (e.g. display a green check mark or a stop sign). The exact details of the implementation (e.g. page layout, API spec) are left entirely to your creativity. We are aware that this validation could be done on the Frontend, but we want you to showcase that you can make an API request and write some backend code :)

Here is an example of a validation algorithm that can be used:

1. The expiry date of the credit card (year and month) must be AFTER present time
1. The CVV (security code) of the credit card must be exactly 3 digits long
    * Unless it’s an American Express card, in which case the CVV must be exactly 4 digits long
    * American Express are cards whose PAN (card numbers) starts with either “34” or “37”
1. The PAN (card number) is between 16 and 19 digits long
1. Last digit of the PAN (card number) is checked using Luhn’s algorithm

We’d like to see you implement at least Points 1 to 3 so that we have something to discuss, but feel free to give Point 4 a try as well :)

## Submitting your solution

As mentioned, your solution should be submitted in the form of a git repository. The solution can be implemented using any programming language you like, but please make sure to include a `README` file explaining how to run your solution (especially if it consists of separate backend and frontend apps).

Send the link to your git repository containing the solution back to the admissions email which provided you with the task.

We will examine your solution, and follow-up with further interview steps if the solution meets our requirements :)
