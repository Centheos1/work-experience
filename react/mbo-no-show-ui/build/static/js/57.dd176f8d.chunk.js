(this["webpackJsonpmbo-no-show"]=this["webpackJsonpmbo-no-show"]||[]).push([[57],{531:function(o,i,t){"use strict";t.r(i),t.d(i,"ion_icon",(function(){return l}));var e=t(136),n=t(267),r=function o(i){if(1===i.nodeType){if("script"===i.nodeName.toLowerCase())return!1;for(var t=0;t<i.attributes.length;t++){var e=i.attributes[t].value;if(Object(n.d)(e)&&0===e.toLowerCase().indexOf("on"))return!1}for(t=0;t<i.childNodes.length;t++)if(!o(i.childNodes[t]))return!1}return!0},s=new Map,c=new Map,a=function(o){var i=c.get(o);if(!i){if("undefined"===typeof fetch)return s.set(o,""),Promise.resolve();i=fetch(o).then((function(i){if(i.ok)return i.text().then((function(i){s.set(o,function(o){if(o&&"undefined"!==typeof document){var i=document.createElement("div");i.innerHTML=o;for(var t=i.childNodes.length-1;t>=0;t--)"svg"!==i.childNodes[t].nodeName.toLowerCase()&&i.removeChild(i.childNodes[t]);var e=i.firstElementChild;if(e&&"svg"===e.nodeName.toLowerCase()){var n=e.getAttribute("class")||"";if(e.setAttribute("class",(n+" s-ion-icon").trim()),r(e))return i.innerHTML}}return""}(i))}));s.set(o,"")})),c.set(o,i)}return i},l=function(){function o(o){Object(e.e)(this,o),this.isVisible=!1,this.mode=h(),this.lazy=!1}return o.prototype.connectedCallback=function(){var o=this;this.waitUntilVisible(this.el,"50px",(function(){o.isVisible=!0,o.loadIcon()}))},o.prototype.disconnectedCallback=function(){this.io&&(this.io.disconnect(),this.io=void 0)},o.prototype.waitUntilVisible=function(o,i,t){var e=this;if(this.lazy&&"undefined"!==typeof window&&window.IntersectionObserver){var n=this.io=new window.IntersectionObserver((function(o){o[0].isIntersecting&&(n.disconnect(),e.io=void 0,t())}),{rootMargin:i});n.observe(o)}else t()},o.prototype.loadIcon=function(){var o=this;if(this.isVisible){var i=Object(n.c)(this);i&&(s.has(i)?this.svgContent=s.get(i):a(i).then((function(){return o.svgContent=s.get(i)})))}if(!this.ariaLabel){var t=Object(n.b)(this.name,this.icon,this.mode,this.ios,this.md);t&&(this.ariaLabel=t.replace(/\-/g," "))}},o.prototype.render=function(){var o,i,t=this.mode||"md",n=this.flipRtl||this.ariaLabel&&(this.ariaLabel.indexOf("arrow")>-1||this.ariaLabel.indexOf("chevron")>-1)&&!1!==this.flipRtl;return Object(e.d)(e.a,{role:"img",class:Object.assign(Object.assign((o={},o[t]=!0,o),d(this.color)),(i={},i["icon-"+this.size]=!!this.size,i["flip-rtl"]=!!n&&"rtl"===this.el.ownerDocument.dir,i))},this.svgContent?Object(e.d)("div",{class:"icon-inner",innerHTML:this.svgContent}):Object(e.d)("div",{class:"icon-inner"}))},Object.defineProperty(o,"assetsDirs",{get:function(){return["svg"]},enumerable:!1,configurable:!0}),Object.defineProperty(o.prototype,"el",{get:function(){return Object(e.b)(this)},enumerable:!1,configurable:!0}),Object.defineProperty(o,"watchers",{get:function(){return{name:["loadIcon"],src:["loadIcon"],icon:["loadIcon"]}},enumerable:!1,configurable:!0}),o}(),h=function(){return"undefined"!==typeof document&&document.documentElement.getAttribute("mode")||"md"},d=function(o){var i;return o?((i={"ion-color":!0})["ion-color-"+o]=!0,i):null};l.style=":host{display:inline-block;width:1em;height:1em;contain:strict;fill:currentColor;-webkit-box-sizing:content-box !important;box-sizing:content-box !important}:host .ionicon{stroke:currentColor}.ionicon-fill-none{fill:none}.ionicon-stroke-width{stroke-width:32px;stroke-width:var(--ionicon-stroke-width, 32px)}.icon-inner,.ionicon,svg{display:block;height:100%;width:100%}:host(.flip-rtl) .icon-inner{-webkit-transform:scaleX(-1);transform:scaleX(-1)}:host(.icon-small){font-size:18px !important}:host(.icon-large){font-size:32px !important}:host(.ion-color){color:var(--ion-color-base) !important}:host(.ion-color-primary){--ion-color-base:var(--ion-color-primary, #3880ff)}:host(.ion-color-secondary){--ion-color-base:var(--ion-color-secondary, #0cd1e8)}:host(.ion-color-tertiary){--ion-color-base:var(--ion-color-tertiary, #f4a942)}:host(.ion-color-success){--ion-color-base:var(--ion-color-success, #10dc60)}:host(.ion-color-warning){--ion-color-base:var(--ion-color-warning, #ffce00)}:host(.ion-color-danger){--ion-color-base:var(--ion-color-danger, #f14141)}:host(.ion-color-light){--ion-color-base:var(--ion-color-light, #f4f5f8)}:host(.ion-color-medium){--ion-color-base:var(--ion-color-medium, #989aa2)}:host(.ion-color-dark){--ion-color-base:var(--ion-color-dark, #222428)}"}}]);
//# sourceMappingURL=57.dd176f8d.chunk.js.map