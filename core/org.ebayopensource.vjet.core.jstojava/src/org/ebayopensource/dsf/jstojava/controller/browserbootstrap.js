var browserMappings = {
	'abbr' :   'HTMLAbbrElement',
	'acronym' :   'HTMLAcronymElement',
	'address' :   'HTMLAddressElement',
	'a' :   'HTMLAnchorElement',
	'applet' :   'HTMLAppletElement',
	'area' :   'HTMLAreaElement',
	'article' :   'HTMLArticleElement',
	'aside' :   'HTMLAsideElement',
	'audio' :   'HTMLAudioElement',
	'b' :   'HTMLBElement',
	'base' :   'HTMLBaseElement',
	'basefont' :   'HTMLBaseFontElement',
	'bb' :   'HTMLBbElement',
	'bdo' :   'HTMLBDOElement',
	'blockquote' :   'HTMLBlockquoteElement',
	'body' :   'HTMLBodyElement',
	'br' :   'HTMLBRElement',
	'button' :   'HTMLButtonElement',
	'canvas' :   'HTMLCanvasElement',
	'center' :   'HTMLCenterElement',
	'caption' :   'HTMLTableCaptionElement',
	'caption' :   'HTMLTableCaptionElement',
	'cite' :   'HTMLCiteElement',
	'code' :   'HTMLCodeElement',
	'col' :   'HTMLTableColElement',
	'colgroup' :   'HTMLColgroupElement',
	'dd' :   'HTMLDdElement',
	'del' :   'HTMLDelElement',
	'details' :   'HTMLDetailsElement',
	'dfn' :   'HTMLDfnElement',
	'dir' :   'HTMLDirectoryElement',
	'div' :   'HTMLDivElement',
	'dl' :   'HTMLDListElement',
	'dt' :   'HTMLDtElement',
	'datagrid' :   'HTMLDataGridElement',
	'datalist' :   'HTMLDataListElement',
	'details' :   'HTMLDetailsElement',
	'dialog' :   'HTMLDialogElement',
	'em' :   'HTMLEmElement',
	'embed' :   'HTMLEmbedElement',
	'fieldset' :   'HTMLFieldSetElement',
	'figure' :   'HTMLFigureElement',
	'font' :   'HTMLFontElement',
	'footer' :   'HTMLFooterElement',
	'form' :   'HTMLFormElement',
	'frame' :   'HTMLFrameElement',
	'head' :   'HTMLHeadElement',
	'header' :   'HTMLHeaderElement',
	'hgroup' :   'HTMLHGroupElement',
	'h1' :   'HTMLHeadingElement',
	'h2' :   'HTMLHeadingElement',
	'h3' :   'HTMLHeadingElement',
	'h4' :   'HTMLHeadingElement',
	'h5' :   'HTMLHeadingElement',
	'h6' :   'HTMLHeadingElement',
	'hr' :   'HTMLHRElement',
	'html' :   'HTMLHtmlElement',
	'i' :   'HTMLIElement',
	'iframe' :   'HTMLIFrameElement',
	'img' :   'HTMLImageElement',
	'input' :   'HTMLInputElement',
	'ins' :   'HTMLInsElement',
	'isindex' :   'HTMLIsIndexElement',
	'kbd' :   'HTMLKbdElement',
	'keygen' :   'HTMLKeyGenElement',
	'label' :   'HTMLLabelElement',
	'legend' :   'HTMLLegendElement',
	'li' :   'HTMLLIElement',
	'link' :   'HTMLLinkElement',
	'map' :   'HTMLMapElement',
	'mark' :   'HTMLMarkElement',
	'marquee' :   'HTMLMarqueeElement',
	'menu' :   'HTMLMenuElement',
	'meta' :   'HTMLMetaElement',
	'meter' :   'HTMLMeterElement',
	'mod' :   'HTMLModElement',
	'nav' :   'HTMLNavElement',
	'nobr' :   'HTMLNoBrElement',
	'noframes' :   'HTMLNoFramesElement',
	'noscript' :   'HTMLNoScriptElement',
	'object' :   'HTMLObjectElement',
	'ol' :   'HTMLOListElement',
	'optgroup' :   'HTMLOptGroupElement',
	'option' :   'HTMLOptionElement',
	'output' :   'HTMLOutputElement',
	'p' :   'HTMLParagraphElement',
	'param' :   'HTMLParamElement',
	'pre' :   'HTMLPreElement',
	'progress' :   'HTMLProgressElement',
	'quote' :   'HTMLQuoteElement',
	'rp' :   'HTMLRpElement',
	'rt' :   'HTMLRtElement',
	'ruby' :   'HTMLRubyElement',
	's' :   'HTMLSElement',
	'samp' :   'HTMLSampElement',
	'script' :   'HTMLScriptElement',
	'section' :   'HTMLSectionElement',
	'select' :   'HTMLSelectElement',
	'small' :   'HTMLSmallElement',
	'source' :   'HTMLSourceElement',
	'span' :   'HTMLSpanElement',
	'strike' :   'HTMLStrikeElement',
	'strong' :   'HTMLStrongElement',
	'style' :   'HTMLStyleElement',
	'sub' :   'HTMLSubElement',
	'sup' :   'HTMLSupElement',
	'table' :   'HTMLTableElement',
	'textarea' :   'HTMLTextAreaElement',
	'th' :   'HTMLThElement',
	'time' :   'HTMLTimeElement',
	'title' :   'HTMLTitleElement',
	'tt' :   'HTMLTtElement',
	'u' :   'HTMLUElement',
	'ul' :   'HTMLUListElement',
	'var' :   'HTMLVarElement',
	'video' :   'HTMLVideoElement',
	'wbr' :   'HTMLWbrElement'
};

var factoryFunctionMappings = {
	'HTMLDocument:createElement' : function(){ 
		var arg = arguments[0].toString(); 
	
		// if(typeof(arg)!="undefined"){ 
		// modify for single quote and double arg = arg.replace("\'",""); 
		 arg = arg.replace("\'","");
		 arg = arg.replace("\"","");
		 arg = arg.replace("\"","");
		
		var mapping = browserMappings[arg.toLowerCase()];
		if(mapping!=null){
			return mapping;
		}
		//} }, 'Document:getElementsByTagName' : function(arg){ var arg = arguments[0]; 
		// if(typeof(arg)!="undefined"){ 
		// modify for single quote and double 
		// arg = arg.replace("\'",""); 
		// arg = arg.replace("\"",""); return browserMappings[arg.toLowerCase()] + '[]'; 
		// } } }
	},
	'Document:getElementsByTagName' : function(arg){
		var arg = arguments[0];
		
		 arg = arg.replace("\'","");
		 arg = arg.replace("\"","");
		 arg = arg.replace("\"","");
	//	  if(typeof(arg)!="undefined"){  // modify for single quote and double
			// arg = arg.replace("\'","");
			// arg = arg.replace("\"","");
		 var mapping = browserMappings[arg.toLowerCase()] + '[]'; 
		 //var mapping = '[' +browserMappings[arg.toLowerCase()] + '[] + NodeList]';
			
		 if(mapping!=null){
				return mapping;
			}
		 
		
	//	  }
		
	}
}