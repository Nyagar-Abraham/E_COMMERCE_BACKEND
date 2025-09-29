<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Password Reset</title>
    <style type="text/css">
        body { margin: 0; padding: 0; background-color: #f6f6f6; font-family: Arial, sans-serif; }
        table { border-collapse: collapse; }
        td { padding: 0; }
        .container { width: 100%; max-width: 600px; }
        .header { padding: 40px 30px; text-align: center; }
        .content { padding: 30px; background-color: #ffffff; }
        .footer { padding: 30px 30px 40px 30px; text-align: center; color: #777777; font-size: 12px; }
        .button { background-color: #007bff; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer; border-radius: 5px; }
        .button:hover { background-color: #0056b3; }
        h1 { font-size: 24px; color: #333333; margin-top: 0; }
        p { font-size: 16px; line-height: 1.5; color: #555555; }
        .warning { color: #cc0000; font-weight: bold; }
        a { color: #007bff; text-decoration: none; }
    </style>
</head>
<body>
<center style="width: 100%; background-color: #f6f6f6;">
    <table align="center" border="0" cellpadding="0" cellspacing="0" class="container">
        <!-- Header -->
        <tr>
            <td class="header">
                <#-- Replace with your own logo URL -->
                <img src="${logo_url}" alt="${company_name} Logo" width="150" style="display: block;" />
            </td>
        </tr>
        <!-- Content -->
        <tr>
            <td class="content">
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td>
                            <h1>Password Reset Request</h1>
                            <p>Hello <#if user_name??>${user_name}<#else>there</#if>,</p>
                            <p>We received a request to reset the password for your ${company_name} account.</p>
                            <p>To reset your password, please click the button below. This link is valid for <strong>${expiration_time}</strong>.</p>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" style="padding: 20px 0 30px 0;">
                            <a href="${reset_url}" target="_blank" class="button">Reset Password</a>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p class="warning">If you did not request a password reset, please ignore this email. No changes will be made to your account.</p>
                            <p>If you have any questions, please contact our support team at <a href="mailto:${support_email}">${support_email}</a>.</p>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <!-- Footer -->
        <tr>
            <td class="footer">
                <p>&copy; ${current_year} ${company_name}. All rights reserved.</p>
                <p>
                    <a href="${website_url}">Website</a> |
                    <a href="${unsubscribe_url}">Unsubscribe</a>
                </p>
            </td>
        </tr>
    </table>
</center>
</body>
</html>
