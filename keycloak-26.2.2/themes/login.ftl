<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "title">
        ${msg("loginTitle",(realm.displayName!''))}
    <#elseif section = "header">
        <link href="https://fonts.googleapis.com/css?family=Muli" rel="stylesheet"/>
        <link href="${url.resourcesPath}/img/favicon.png" rel="icon"/>
        <script>
            function togglePassword() {
                var x = document.getElementById("password");
                var v = document.getElementById("vi");
                if (x.type === "password") {
                    x.type = "text";
                    v.src = "${url.resourcesPath}/img/eye.png";
                } else {
                    x.type = "password";
                    v.src = "${url.resourcesPath}/img/eye-off.png";
                }
            }

            window.onload = function() {
                debugger;
                var loginForm = document.getElementById('kc-form-login');
                if (loginForm) {
                    loginForm.onsubmit = function(e) {
                        // Lấy giá trị từ các input
                        var usernameInput = document.getElementById('username');
                        var companyCodeInput = document.getElementById('company_code');
                        
                        if (usernameInput && companyCodeInput) {
                            var username = usernameInput.value;
                            var companyCode = companyCodeInput.value;
                            
                            // Kiểm tra nếu cả hai trường đều có giá trị
                            if (username && companyCode) {
                                // Gán lại giá trị cho username theo format companyCode@username
                                usernameInput.value = companyCode + '@' + username;
                            }
                        }
                        
                        // Cho phép form submit
                        return true;
                    };
                }
            };
        </script>
    <#elseif section = "form">
        <div>
            <img class="logo" src="${url.resourcesPath}/img/alfresco-logo.svg" alt="Alfresco">
        </div>
        <div class="box-container">
            <div>
                <p class="application-name">Alfresco Identity Service</p>
            </div>
        <#if realm.password>
            <div>
               <form id="kc-form-login" class="form" onsubmit="return true;" action="${url.loginAction}" method="post">
                    <input id="username" class="login-field" placeholder="${msg("username")}" type="text" name="username" tabindex="0">
                    <div>
                        <label class="visibility" id="v" onclick="togglePassword()"><img id="vi" src="${url.resourcesPath}/img/eye-off.png" alt="${msg("togglePasswordVisibility")}"></label>
                    </div>
                <input id="password" class="login-field" placeholder="${msg("password")}" type="password" name="password" tabindex="0">
                <input class="submit" type="submit" value="${msg("doLogIn")}" tabindex="0">
                </form>
            </div>
        </#if>
        <#if social.providers?? && social.providers?has_content>
            <p class="para">${msg("selectAlternative")}</p>
            <div id="social-providers">
                <#list social.providers as p>
                <input class="social-link-style" type="button" onclick="location.href='${p.loginUrl}';" value="${p.displayName}"/>
                </#list>
            </div>
        </#if>
        </div>
        <div>
            <p class="copyright">&copy; ${msg("copyright", "${.now?string('yyyy')}")}</p>
        </div>
    </#if>
</@layout.registrationLayout>