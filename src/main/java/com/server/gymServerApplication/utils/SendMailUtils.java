package com.server.gymServerApplication.utils;

import java.util.function.Function;

public class SendMailUtils {
    public static Function<String, String> Template(String code, String message) {
        return (input) -> "<body style=\"font-family: 'Poppins', Arial, sans-serif\">\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\" style=\"padding: 20px;\">\n" +
                "            <table class=\"content\" width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse; border: 1px solid #cccccc;\">\n" +
                "\n" +
                "                <tr>\n" +
                "                    <td class=\"header\" style=\"background-color: #345C72; padding: 40px; text-align: center; color: white; font-size: 24px;\">\n" +
                message +
                "                       \n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <tr>\n" +
                "                    <td class=\"body\" style=\"padding: 40px; text-align: left; font-size: 16px; line-height: 1.6;\">\n" +
                "                        Xin chao," + input + " <br>\n" +
                "                        Ma xac nhan cua ban la.\n" +
                "                        <br><br>\n" +
                "                        Khong chia se no cho bat ki ai !\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 0px 40px 0px 40px; text-align: center;\">\n" +
                "                        <table cellspacing=\"0\" cellpadding=\"0\" style=\"margin: auto;\">\n" +
                "                            <tr>\n" +
                "                                <td align=\"center\" style=\"background-color: #345C72; padding: 10px 20px; border-radius: 5px;\">\n" +
                "                                    <a href=\"\" target=\"_blank\" style=\"color: #ffffff; text-decoration: none; font-weight: bold;\">" + code + "</a>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"body\" style=\"padding: 40px; text-align: left; font-size: 16px; line-height: 1.6;\">\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"footer\" style=\"background-color: #333333; padding: 40px; text-align: center; color: white; font-size: 14px;\">\n" +
                "                        Copyright &copy; 2024 | NewsExpress\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>";
    }

}
