import { browser, by, element } from 'protractor';

export class AppPage {
  navigateTo() {
    return browser.get('/');
  }


  getHeadingText() {
    return element(by.id(".app-heading")).getText();
  }

  getFirstNameField(){
    return element(by.id('#first-name-field'));
  }

  getLastNameField(){
    return element(by.id('#last-name-field'));
  }

  getEmailField(){
    return element(by.id('#email-field'));
  }

  getPasswordField(){
    return element(by.id('#password-field'));
  }

  getRetypePasswordField(){
    return element(by.id('#retype-password-field'));
  }

  getUserIdField(){
    return element(by.id('#userId-field'));
  }
  getSearchText(){
    return element(by.id('#search-text'));
  }
 getArticleCards(){
   return element.all(by.id('#article-card'));
 }
  clickLoginButton(){
    return element(by.id('#btn-login')).click();
  }
  clickLogoutButton(){
    return element(by.id('#logout-btn')).click();
  }
  clickSignUpButton(){
    return element(by.id('#btn-signup')).click();
  }

  clickGotoSignUpButton(){
    return element(by.id('#btn-goto-signup')).click();
  }

  // clickAddtoWishlistButton(){
  //   element.all(by.id('#article-card')).first().element
  //        .(by.id('#article-button')).click();
    
  // }

  getNavBarItem(){
    return element.all(by.id('#nav-bar')).first();
  }
 
}
