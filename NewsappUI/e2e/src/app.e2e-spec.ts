import { AppPage } from './app.po';
import {browser, protractor} from 'protractor';

describe('news App', () => {
  let page: AppPage;
  const firstName = 'EntToEnd';
  const lastName = 'Test';
  const password = '123Test$';
  const email = 'e2e@test.com';
  const searchText = 'india';

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display app heading', () => {
    page.navigateTo();
    expect(page.getHeadingText()).toEqual('NewsApp');
  });
  it('should navigate to the signup page', () => {
    page.clickGotoSignUpButton();
    expect(browser.getCurrentUrl()).toContain('/#/signup');
  });

  it('should be able to signup', () => {
    page.getFirstNameField().sendKeys(firstName);
    page.getLastNameField().sendKeys(lastName);
    page.getEmailField().sendKeys(email);
    page.getPasswordField().sendKeys(password);
    page.getRetypePasswordField().sendKeys(password);
    page.clickSignUpButton();
    expect(browser.getCurrentUrl()).toContain('/#/login');
  });

 it('should be able to login', () => {
     page.getUserIdField().sendKeys(email);
     page.getPasswordField().sendKeys(password);
     page.clickLoginButton();
     expect(browser.getCurrentUrl()).toContain('/#/search');
  });

  
  it('should get search results more than 6', () => {
    page.getSearchText().sendKeys(searchText);
    page.getSearchText().sendKeys(protractor.Key.TAB);
    expect(page.getArticleCards().count()).toBeGreaterThanOrEqual(6);
  });
  //  it('should add article to favourite list', () =>  {
  //    page.clickAddtoWishlistButton();
  //    page.getNavBarItem().sendKeys('My WishLists');
  //    page.getNavBarItem().sendKeys(protractor.Key.ENTER);
  //    expect(page.getArticleCards().count()).toEqual(1);
  //  });

  it('should log out succesfully', () => {
     page.clickLogoutButton();
     expect(browser.getCurrentUrl()).toContain('/#/login');
  });
  it('should NOT be able to navigate to dashboard', () => {
    browser.get('/#/search');
     expect(browser.getCurrentUrl()).toContain('/#/login');
  });
});
