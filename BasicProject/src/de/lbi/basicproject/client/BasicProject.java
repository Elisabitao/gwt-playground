package de.lbi.basicproject.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BasicProject implements EntryPoint {
	
	
	
	HTMLPanel homePanel;
	HTMLPanel productsPanel;
	HTMLPanel contactPanel;
	TabLayoutPanel content; 
	
	/**
	 * Numerical values to reference the tabs the content pages are held in.
	 */
	static final int DECK_HOME = 0;
	static final int DECK_PRODUCTS = 1;
	static final int DECK_CONTACT = 2;
	
	/**
	 * Strings representing the history tokens we will use to indicate which tab content
	 * the user is viewing.
	 */
	static final String TOKEN_HOME = "Home";
	static final String TOKEN_PRODUCTS = "Products";
	static final String TOKEN_CONTACT = "Contact";
	

	enum Pages {
		
		HOME(DECK_HOME, TOKEN_HOME), PRODUCTS(DECK_PRODUCTS, TOKEN_PRODUCTS), CONTACT(DECK_CONTACT, TOKEN_CONTACT);
		
		// Holds the card number in the deck this enumeration relates to. 
		private int val;
		// Holds the history token value this enumeration relates to.
		private String text;
		
		// Simple method to get the card number in the deck this enumeration relates to.
		int getVal(){return val;}
		// Simple method to get the history token this enumeration relates to.
		String getText(){return text;}

		// Enumeration constructor that stores the card number and history token for this enumeration.
		Pages(int val, String text) {
			this.val = val;
			this.text = text;
		};
	}
	
	public void onModuleLoad() {		
		// Create the user interface
		setUpGui();		
		// Set up history management
		setUpHistoryManagement();
		// Set up all the event handling required for the application.
		setUpEventHandling();	
	}

	
	private void setUpGui(){
		buildTabContent();
		//wrapExistingSearchButton();
		//insertLogo();
		//createFeedbackTab();
		//styleTabPanelUsingUIObject();
		//styleButtonUsingDom();

		//RootPanel.get().add(feedback);
		//RootPanel logoSlot = RootPanel.get("logo");
		//if(logoSlot!=null)logoSlot.add(logo);
		//RootPanel contentSlot = RootPanel.get("content");
		//if(contentSlot!=null)contentSlot.add(content);
	}


	private void setUpHistoryManagement(){};
	private void setUpEventHandling(){}
	

	private void buildTabContent(){
		
		// First retrieve the existing content for the pages from the HTML page
				homePanel = new HTMLPanel(getContent(Pages.HOME.getText()));
				productsPanel = new HTMLPanel(getContent(Pages.PRODUCTS.getText()));
				contactPanel = new HTMLPanel(getContent(Pages.CONTACT.getText()));
				
				// set the style of HTMLPanels
				homePanel.addStyleName("htmlPanel");
				productsPanel.addStyleName("htmlPanel");
				contactPanel.addStyleName("htmlPanel");
				
				// Create the tab panel widget
				content = new TabLayoutPanel(20, Unit.PX);

				// Add the content we have just created to the tab panel widget
				content.add(homePanel, Pages.HOME.getText());
				content.add(productsPanel, Pages.PRODUCTS.getText());
				content.add(contactPanel, Pages.CONTACT.getText());
				
				// Indicate that we should show the HOME tab initially.
				content.selectTab(DECK_HOME);
	}
	
	/**
	 * Returns the HTML content of an existing DOM element on the HTML page.
	 * 
	 * Should be careful with these type of methods if you are going to use the data
	 * later to ensure people are not injecting scripts into your code.
	 * In our example, we control the HTML that the data is retrieved from.
	 * 
	 * @param id The id of the DOM element we wish to get the content for.
	 * @return The HTML content of the DOM element.
	 */
	private String getContent(String id) {

		// Initialise the return string.
		String toReturn = "";
		
		// Find the DOM element by the id passed in.
		Element element = DOM.getElementById(id);
		
		// Make sure we've found the DOM element and then manipulate it.
		if (element!=null){
		
			// Get the inner HTML content of the DOM element.
			toReturn = DOM.getInnerHTML(element);

		
			// Set the inner value of the DOM element to an empty string
			// if we don't do this, then it is still displayed on the screen.
			DOM.setInnerText(element, "");
		
			// Comment the following two lines of code out to not use SafeHTML to create the response.
			// If we use it, then this makes sure the HTML we have from the HTML page is sanitized against 
			// any XSS attacks.  In this example's case, the hyperlink in contacts page is sanitized, i.e.
			// you cannot click on it.  
			// This can be seen as overkill in this case, but security should always be at the heart of
			// your development (it is too large a topic for us to cover within the GWT in Action book).
			SafeHtml sfHtml = SimpleHtmlSanitizer.sanitizeHtml(toReturn);
			toReturn = sfHtml.asString();
		} else {
			// If we can't find the content then let's just put an error message in the content
			// (You can test this by changing the id of the DOM elements in the HTML page - you probably need
			// to clear your browser's cache to see the impact of any changes you make).
			toReturn = "Unable to find "+id+" content in HTML page";
		}
		return toReturn;
	}
	
	
}


