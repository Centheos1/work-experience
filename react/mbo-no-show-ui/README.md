# PWA Template

To get started:
## Step 1
npm install
## Step 2
npm start

Alternatively test site url is: https://mbo-no-show-ui-42d2e.firebaseapp.com/

Ionic React course Refernce: https://www.udemy.com/course/ionic-react

## Links

 * [Firebase: Get Started](https://firebase.google.com/docs/web/setup)
 * [Firebase Authentication](https://firebase.google.com/docs/auth)
 * [Cloud Firestore](https://firebase.google.com/docs/firestore)



***Default Bitbucket Notes
**Edit a file, create a new file, and clone from Bitbucket in under 2 minutes**

When you're done, you can delete the content in this README and update the file with details for others getting started with your repository.

*We recommend that you open this README in another tab as you perform the tasks below. You can [watch our video](https://youtu.be/0ocf7u76WSo) for a full demo of all the steps in this tutorial. Open the video in a new tab to avoid leaving Bitbucket.*

---

## Edit a file

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. Click **Source** on the left side.
2. Click the README.md link from the list of files.
3. Click the **Edit** button.
4. Delete the following text: *Delete this line to make a change to the README from Bitbucket.*
5. After making your change, click **Commit** and then **Commit** again in the dialog. The commit page will open and you’ll see the change you just made.
6. Go back to the **Source** page.

---

## Create a file

Next, you’ll add a new file to this repository.

1. Click the **New file** button at the top of the **Source** page.
2. Give the file a filename of **contributors.txt**.
3. Enter your name in the empty file space.
4. Click **Commit** and then **Commit** again in the dialog.
5. Go back to the **Source** page.

Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

## Clone a repository

Use these steps to clone from SourceTree, our client for using the repository command-line free. Cloning allows you to work on your files locally. If you don't yet have SourceTree, [download and install first](https://www.sourcetreeapp.com/). If you prefer to clone from the command line, see [Clone a repository](https://confluence.atlassian.com/x/4whODQ).

1. You’ll see the clone button under the **Source** heading. Click that button.
2. Now click **Check out in SourceTree**. You may need to create a SourceTree account or log in.
3. When you see the **Clone New** dialog in SourceTree, update the destination path and name if you’d like to and then click **Clone**.
4. Open the directory you just created to see your repository’s files.

Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).


## Deploy as app
Set Up
- npm install @capacitor/cli --save-exact
- npm install @capacitor/cli --save-exact --save-dev
- npx cap init --web-dir build --npm-client npm NoShow com.centheos.noshow

- This was painful for iOS. 
- Use brew install cococapods
- npx cap add ios
- npm run build
- npx cap sync
- npx cap open ios

- Hopefully android is easier
- npx cap add android
- npx cap open android
- Nope I have SDK issues