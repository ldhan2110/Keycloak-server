<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>
    <#if section = "header">
        Đăng nhập
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                    <div class="form-group">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input tabindex="1" id="username" name="username" value="${(username!'')}" type="text" autofocus autocomplete="off" />
                    </div>

                    <div class="form-group">
                        <label for="password" class="form-label">Mật khẩu</label>
                        <input tabindex="2" id="password" name="password" type="password" autocomplete="off" />
                    </div>

                    <div class="form-group">
                        <label for="company_code" class="form-label">Mã công ty</label>
                        <input tabindex="3" id="company_code" name="company_code" type="text" autocomplete="off" />
                    </div>

                    <div id="kc-form-buttons" class="form-group">
                        <input type="hidden" id="id-hidden-input" name="credentialId" <#if auth?has_content && auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
                        <input tabindex="4" name="login" id="kc-login" type="submit" value="Đăng nhập"/>
                    </div>
                </form>
            </div>
        </div>
    </#if>
</@layout.registrationLayout>
