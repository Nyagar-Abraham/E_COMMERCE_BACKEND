<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Verification</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
            line-height: 1.6;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            color: #333333;
        }
        .email-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
            overflow: hidden;
            border: 1px solid #e0e0e0;
        }
        .header {
            background-color: #1a73e8;
            color: #ffffff;
            padding: 20px;
            text-align: center;
        }
        .content {
            padding: 30px;
        }
        .content h1 {
            font-size: 24px;
            color: #1a73e8;
            margin-top: 0;
        }
        .content p {
            margin-bottom: 20px;
        }
        .button-container {
            text-align: center;
            margin: 30px 0;
        }
        .verification-button {
            display: inline-block;
            background-color: #1a73e8;
            color: #ffffff;
            text-decoration: none;
            padding: 12px 24px;
            border-radius: 5px;
            font-weight: bold;
            font-size: 16px;
        }
        .footer {
            background-color: #f1f1f1;
            padding: 20px;
            text-align: center;
            font-size: 12px;
            color: #999999;
            border-top: 1px solid #e0e0e0;
        }
        .footer a {
            color: #1a73e8;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="email-container">
    <div class="header">
        <h2>Verify Your Email Address</h2>
    </div>
    <div class="content">
        <#-- A FreeMarker comment is used here to indicate that you can add dynamic content -->
        <p>Hello <#if user.firstName??>${user.firstName}<#else>user</#if>,</p>
        <p>Thank you for registering! Please click the button below to verify your email address and activate your account.</p>
        <div class="button-container">
            <a href="${verificationUrl}" class="verification-button">Verify Email Address</a>
        </div>
        <p>This link will be valid for 24 hours. If the button does not work, please copy and paste the following URL into your web browser:</p>
        <p><a href="${verificationUrl}">Verify</a></p>
        <p>If you did not create an account, no further action is required.</p>
    </div>
    <div class="footer">
        <p>
            &copy; <#if companyName??>${companyName}<#else>Your Company</#if> <#if currentYear??>${currentYear}<#else>${.now?string("yyyy")}</#if>
            <br>
            <a href="${companyWebsiteUrl}">YourWebsite.com</a>
        </p>
    </div>
</div>
</body>
</html>