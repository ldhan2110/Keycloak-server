<!DOCTYPE html>
<html>
<head>
  <title>Sign In</title>
  <style>
    * {
      box-sizing: border-box;
    }

    body {
      margin: 0;
      padding: 0;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f3f4f6;
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100vh;
    }

    .login-wrapper {
      background: white;
      padding: 40px 30px;
      border-radius: 12px;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
      width: 100%;
      max-width: 360px;
      display: flex;
      flex-direction: column;
      align-items: center;
      animation: fadeIn 0.4s ease;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    h2 {
      margin-bottom: 24px;
      color: #1f2937;
      font-size: 24px;
      font-weight: 600;
      text-align: center;
      width: 100%;
    }

    form {
      width: 100%;
    }

    input[type="text"],
    input[type="password"] {
      width: 100%;
      padding: 12px 14px;
      margin-bottom: 16px;
      border: 1px solid #d1d5db;
      border-radius: 8px;
      font-size: 15px;
      transition: all 0.2s ease-in-out;
      display: block;
    }

    input[type="text"]:focus,
    input[type="password"]:focus {
      border-color: #3b82f6;
      box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
      outline: none;
    }

    input[type="submit"] {
      width: 100%;
      padding: 12px;
      background-color: #3b82f6;
      border: none;
      color: white;
      font-size: 16px;
      font-weight: 500;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.2s ease;
    }

    input[type="submit"]:hover {
      background-color: #2563eb;
    }

    .error-message {
      background-color: #fee2e2;
      color: #b91c1c;
      padding: 10px;
      border-radius: 6px;
      margin-bottom: 15px;
      width: 100%;
      text-align: center;
      font-size: 14px;
    }
  </style>
</head>
<body>
  <div class="login-wrapper">
    <h2>Sign In</h2>

    <#if message?has_content>
      <div class="error-message">${message.summary}</div>
    </#if>

    <form id="kc-form-login" action="${url.loginAction}" method="post">
      <input type="hidden" name="credentialId" value="${credentialId!}" />
      <input type="text" id="username" name="username" value="${login.username!}" placeholder="Username" autofocus />
      <input type="password" id="password" name="password" placeholder="Password" />
      <#if realm.rememberMe && !login.rememberMe?has_content>
        <div style="margin-bottom: 10px;">
          <label>
            <input type="checkbox" id="rememberMe" name="rememberMe" 
                  <#if login.rememberMe?? && login.rememberMe == "on">checked</#if> />
            Remember Me
          </label>
        </div>
      </#if>
      <input type="submit" name="login" value="Login" />
    </form>
  </div>

  <#if realm.registrationAllowed>
    <div style="margin-top: 16px; text-align: center;">
      <a href="${url.registrationUrl}">Don't have an account? Register</a>
    </div>
  </#if>
</body>
</html>
