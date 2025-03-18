"use strict";(self["webpackChunktask_manager"]=self["webpackChunktask_manager"]||[]).push([[791],{7893:function(t,e,o){var n=o(5873),i=o(3713),s=(0,i.a0)({props:{elementAttr:Object,height:[Function,Number,String],hint:String,indicatorSrc:String,onContentReady:Function,onDisposing:Function,onInitialized:Function,onOptionChanged:Function,rtlEnabled:Boolean,visible:Boolean,width:[Function,Number,String]},emits:{"update:isActive":null,"update:hoveredElement":null,"update:elementAttr":null,"update:height":null,"update:hint":null,"update:indicatorSrc":null,"update:onContentReady":null,"update:onDisposing":null,"update:onInitialized":null,"update:onOptionChanged":null,"update:rtlEnabled":null,"update:visible":null,"update:width":null},computed:{instance:function(){return this.$_instance}},beforeCreate:function(){this.$_WidgetClass=n.A,this.$_hasAsyncTemplate=!0}});e.A=s},1196:function(t,e,o){o.d(e,{A:function(){return z}});var n=o(4311),i=o(405),s=o(5478),a=o(6674),r=o(9394),l=o(1137),d=o(850),u=o(9535),c=o(5596),h=o(7049),p=o(4436),m=o(198),g=o(7424),f=c.A.add,v="dx-toast",b=v+"-",w=b+"wrapper",y=b+"content",k=b+"message",_=b+"icon",x="dxToast",C=["info","warning","error","success"],A=[],O=8e3,T={top:{my:"top",at:"top",of:null,offset:"0 0"},bottom:{my:"bottom",at:"bottom",of:null,offset:"0 -20"},center:{my:"center",at:"center",of:null,offset:"0 0"},right:{my:"center right",at:"center right",of:null,offset:"0 0"},left:{my:"center left",at:"center left",of:null,offset:"0 0"}},S={h:0,v:0},D=20;f((function(){u.A.subscribeGlobal(d.A.getDocument(),h.A.down,(function(t){for(var e=A.length-1;e>=0;e--)if(!A[e]._proxiedDocumentDownHandler(t))return}))}));var F=m.A.inherit({_getDefaultOptions:function(){return(0,s.X)(this.callBase(),{message:"",type:"info",displayTime:2e3,position:"bottom center",animation:{show:{type:"fade",duration:400,from:0,to:1},hide:{type:"fade",duration:400,from:1,to:0}},shading:!1,height:"auto",hideTopOverlayHandler:null,preventScrollEvents:!1,closeOnSwipe:!0,closeOnClick:!1})},_defaultOptionsRules:function(){var t={displayTime:(0,g.Nt)()?4e3:2e3,hideOnOutsideClick:!0,animation:{show:{type:"fade",duration:200,from:0,to:1},hide:{type:"fade",duration:200,from:1,to:0}}};return this.callBase().concat([{device:t=>"phone"===t.deviceType,options:(0,l.A)({width:"calc(100vw - ".concat(2*D,"px)")},t)},{device:t=>"tablet"===t.deviceType,options:(0,l.A)({width:"auto",maxWidth:"80vw"},t)},{device:t=>(0,g.Nt)()&&"desktop"===t.deviceType,options:{minWidth:344,maxWidth:568,displayTime:4e3}}])},_init:function(){this.callBase(),this._posStringToObject()},_renderContentImpl:function(){this._message=(0,n.A)("<div>").addClass(k).text(this.option("message")).appendTo(this.$content()),this.setAria("role","alert",this._message),C.includes(this.option("type").toLowerCase())&&this.$content().prepend((0,n.A)("<div>").addClass(_)),this.callBase()},_render:function(){this.callBase(),this.$element().addClass(v),this.$wrapper().addClass(w),this.$content().addClass(b+String(this.option("type")).toLowerCase()),this.$content().addClass(y),this._toggleCloseEvents("Swipe"),this._toggleCloseEvents("Click")},_toggleCloseEvents:function(t){var e="dx"+t.toLowerCase();u.A.off(this.$content(),e),this.option("closeOn"+t)&&u.A.on(this.$content(),e,this.hide.bind(this))},_posStringToObject:function(){if((0,a.Kg)(this.option("position"))){var t=this.option("position").split(" ")[0],e=this.option("position").split(" ")[1];switch(this.option("position",(0,s.X)({boundaryOffset:S},T[t])),e){case"center":case"left":case"right":this.option("position").at+=" "+e,this.option("position").my+=" "+e}}},_show:function(){return this.callBase.apply(this,arguments).always(function(){clearTimeout(this._hideTimeout),this._hideTimeout=setTimeout(this.hide.bind(this),this.option("displayTime"))}.bind(this))},_overlayStack:function(){return A},_zIndexInitValue:function(){return this.callBase()+O},_dispose:function(){clearTimeout(this._hideTimeout),this.callBase()},_optionChanged:function(t){switch(t.name){case"type":this.$content().removeClass(b+t.previousValue),this.$content().addClass(b+String(t.value).toLowerCase());break;case"message":this._message&&this._message.text(t.value);break;case"closeOnSwipe":this._toggleCloseEvents("Swipe");break;case"closeOnClick":this._toggleCloseEvents("Click");break;case"displayTime":break;default:this.callBase(t)}}});(0,p.A)(x,F);var W=F,E=W,$=(0,r.zk)(),B=null,H={};function I(t,e,o){var r=(0,a.Qd)(t)?t:{message:t},l=(0,a.Qd)(e)?e:void 0,d=(0,a.Qd)(e)?void 0:e,{onHidden:u}=r;if(null!==l&&void 0!==l&&l.position){var{position:c}=l,h=l.direction||L(c),p=(0,a.Kg)(c)?c:"".concat(c.top,"-").concat(c.left,"-").concat(c.bottom,"-").concat(c.right),{onShowing:m}=r,g=K(p);M(g,h),(0,s.X)(r,{container:g,_skipContentPositioning:!0,onShowing:function(t){X(g,h,c),null===m||void 0===m||m(t)}})}(0,s.X)(r,{type:d,displayTime:o,onHidden:function(t){(0,n.A)(t.element).remove(),null===u||void 0===u||u(t)}}),B=(0,n.A)("<div>").appendTo((0,i.Uq)()),new E(B,r).show()}var L=t=>(0,a.Kg)(t)&&t.includes("top")?"down-push":"up-push",R=t=>{var e=(0,n.A)("<div>").appendTo((0,i.Uq)());return H[t]=e,e},K=t=>{var e=H[t];return e||R(t)},M=(t,e)=>{var o="dx-toast-stack dx-toast-stack-".concat(e,"-direction");t.removeAttr("class").addClass(o)},X=(t,e,o)=>{var{offsetWidth:n,offsetHeight:i}=t.children().first().get(0),s={toastWidth:n,toastHeight:i,windowHeight:$.innerHeight,windowWidth:$.innerWidth},r=(0,a.Kg)(o)?j(o,s):o,l=q(e,r,s);t.css(l)},j=(t,e)=>{var{toastWidth:o,toastHeight:n,windowHeight:i,windowWidth:s}=e;switch(t){case"top left":return{top:10,left:10};case"top right":return{top:10,right:10};case"bottom left":return{bottom:10,left:10};case"bottom right":return{bottom:10,right:10};case"top center":return{top:10,left:Math.round(s/2-o/2)};case"left center":return{top:Math.round(i/2-n/2),left:10};case"right center":return{top:Math.round(i/2-n/2),right:10};case"center":return{top:Math.round(i/2-n/2),left:Math.round(s/2-o/2)};case"bottom center":default:return{bottom:10,left:Math.round(s/2-o/2)}}},q=(t,e,o)=>{var n,i,s,a,r,l,d,u,c,h,p,m,{toastWidth:g,toastHeight:f,windowHeight:v,windowWidth:b}=o;switch(t.replace(/-push|-stack/g,"")){case"up":return{bottom:null!==(n=e.bottom)&&void 0!==n?n:v-f-e.top,top:"",left:null!==(i=e.left)&&void 0!==i?i:"",right:null!==(s=e.right)&&void 0!==s?s:""};case"down":return{top:null!==(a=e.top)&&void 0!==a?a:v-f-e.bottom,bottom:"",left:null!==(r=e.left)&&void 0!==r?r:"",right:null!==(l=e.right)&&void 0!==l?l:""};case"left":return{right:null!==(d=e.right)&&void 0!==d?d:b-g-e.left,left:"",top:null!==(u=e.top)&&void 0!==u?u:"",bottom:null!==(c=e.bottom)&&void 0!==c?c:""};case"right":return{left:null!==(h=e.left)&&void 0!==h?h:b-g-e.right,right:"",top:null!==(p=e.top)&&void 0!==p?p:"",bottom:null!==(m=e.bottom)&&void 0!==m?m:""}}},z=I},3305:function(t,e){const o={email:"sandra@example.com",avatarUrl:"https://js.devexpress.com/Demos/WidgetsGallery/JSDemos/images/employees/06.png"};e.A={_user:o,loggedIn(){return!!this._user},async logIn(t,e){try{return console.log(t,e),this._user={...o,email:t},{isOk:!0,data:this._user}}catch{return{isOk:!1,message:"Authentication failed"}}},async logOut(){this._user=null},async getUser(){try{return{isOk:!0,data:this._user}}catch{return{isOk:!1}}},async resetPassword(t){try{return console.log(t),{isOk:!0}}catch{return{isOk:!1,message:"Failed to reset password"}}},async changePassword(t,e){try{return console.log(t,e),{isOk:!0}}catch{return{isOk:!1,message:"Failed to change password"}}},async createAccount(t,e){try{return console.log(t,e),{isOk:!0}}catch{return{isOk:!1,message:"Failed to create account"}}}}},918:function(t,e,o){o.r(e),o.d(e,{default:function(){return w}});var n=o(641),i=o(3751);const s={class:"login-link"},a={class:"dx-button-text"},r={key:1};function l(t,e,o,l,d,u){const c=(0,n.g2)("dx-required-rule"),h=(0,n.g2)("dx-email-rule"),p=(0,n.g2)("dx-label"),m=(0,n.g2)("dx-item"),g=(0,n.g2)("dx-button-options"),f=(0,n.g2)("dx-button-item"),v=(0,n.g2)("router-link"),b=(0,n.g2)("dx-load-indicator"),w=(0,n.g2)("dx-form");return(0,n.uX)(),(0,n.CE)("form",{class:"reset-password-form",onSubmit:e[0]||(e[0]=(0,i.D$)(((...t)=>l.onSubmit&&l.onSubmit(...t)),["prevent"]))},[(0,n.bF)(w,{"form-data":l.formData,disabled:l.loading},{resetTemplate:(0,n.k6)((()=>[(0,n.Lk)("div",null,[(0,n.Lk)("span",a,[l.loading?((0,n.uX)(),(0,n.Wv)(b,{key:0,width:"24px",height:"24px",visible:!0})):(0,n.Q3)("",!0),l.loading?(0,n.Q3)("",!0):((0,n.uX)(),(0,n.CE)("span",r,"Reset my password"))])])])),default:(0,n.k6)((()=>[(0,n.bF)(m,{"data-field":"email","editor-type":"dxTextBox","editor-options":{stylingMode:"filled",placeholder:"Email",mode:"email"}},{default:(0,n.k6)((()=>[(0,n.bF)(c,{message:"Email is required"}),(0,n.bF)(h,{message:"Email is invalid"}),(0,n.bF)(p,{visible:!1})])),_:1}),(0,n.bF)(f,null,{default:(0,n.k6)((()=>[(0,n.bF)(g,{"element-attr":{class:"submit-button"},width:"100%",type:"default",template:"resetTemplate","use-submit-behavior":!0})])),_:1}),(0,n.bF)(m,null,{default:(0,n.k6)((()=>[(0,n.Lk)("div",s,[(0,n.eW)(" Return to "),(0,n.bF)(v,{to:"/login-form"},{default:(0,n.k6)((()=>[(0,n.eW)("Sign In")])),_:1})])])),_:1})])),_:1},8,["form-data","disabled"])],32)}o(4114);var d=o(5463),u=o(7893),c=o(1196),h=o(953),p=o(5220),m=o(3305);const g="We've sent a link to reset your password. Check your inbox.";var f={components:{DxForm:d.Ay,DxItem:d.KV,DxLabel:d.jd,DxButtonItem:d.I,DxButtonOptions:d.$3,DxRequiredRule:d.az,DxEmailRule:d.Ll,DxLoadIndicator:u.A},setup(){const t=(0,p.rd)(),e=(0,h.KR)(!1),o=(0,h.Kh)({email:""});async function n(){const{email:n}=o;e.value=!0;const i=await m.A.resetPassword(n);e.value=!1,i.isOk?(t.push("/login-form"),(0,c.A)(g,"success",2500)):(0,c.A)(i.message,"error",2e3)}return{loading:e,formData:o,onSubmit:n}}},v=o(6262);const b=(0,v.A)(f,[["render",l]]);var w=b}}]);
//# sourceMappingURL=login5.8c31eb02.js.map