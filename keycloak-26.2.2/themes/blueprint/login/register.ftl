<!DOCTYPE html>
<html>
<head>
  <title>Register</title>
  <style>
    /* You can reuse login style here */
  </style>
</head>
<body>
  <div class="login-wrapper">
    <h2>Create Account</h2>

    <#if message?has_content>
      <div class="error-message">${message.summary}</div>
    </#if>

    <form id="kc-register-form" action="${url.registrationAction}" method="post">
      <input type="text" name="firstName" placeholder="First Name" value="${register.formData.firstName!}" />
      <input type="text" name="lastName" placeholder="Last Name" value="${register.formData.lastName!}" />
      <input type="text" name="email" placeholder="Email" value="${register.formData.email!}" />
      <input type="text" name="username" placeholder="Username" value="${register.formData.username!}" />
      <input type="password" name="password" placeholder="Password" />
      <input type="password" name="password-confirm" placeholder="Confirm Password" />
      <input type="submit" value="Register" />
    </form>

    <div style="margin-top: 16px; text-align: center;">
      <a href="${url.loginUrl}">Back to login</a>
    </div>
  </div>
</body>
</html>
