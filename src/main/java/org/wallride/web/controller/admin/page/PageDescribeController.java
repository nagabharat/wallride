package org.wallride.web.controller.admin.page;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wallride.core.domain.Page;
import org.wallride.core.service.PageService;
import org.wallride.web.support.DomainObjectDescribeController;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/{language}/pages/describe", method=RequestMethod.GET)
public class PageDescribeController extends DomainObjectDescribeController<Page, PageSearchForm> {
	
	@Inject
	private PageService pageService;
	
	@RequestMapping
	public String describe( 
			@RequestParam long id,
			@RequestParam(required=false) String token,
			Model model,
			HttpSession session) {
		return super.requestMappingDescribe(id, token, model, session);
	}

	@RequestMapping(params="part=delete-dialog")
	public String partDeleteDialog(
			@PathVariable String language,
			@RequestParam long id, Model model) {
		Page page = pageService.readPageById(id, language);
		model.addAttribute("page", page);
		return "/page/describe::#delete-dialog";
	}

	@Override
	protected Class<PageSearchForm> getDomainObjectSearchFormClass() {
		return PageSearchForm.class;
	}

	@Override
	protected String getModelAttributeName() {
		return "page";
	}

	@Override
	protected String getViewName() {
		return "/page/describe";
	}

	@Override
	protected Page readDomainObject(long id) {
		return pageService.readPageById(id, LocaleContextHolder.getLocale().getLanguage());
	}
}