/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui;

import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.PlainDocument;


/**
 *
 * @author Usman
 */
public class NumericTextField extends JTextField
{
    public NumericTextField(int x)
    {
        super(x);
    }
 
    @Override
    protected Document createDefaultModel()
    {
        return new NumericDocument();
    }
 
    private static class NumericDocument extends PlainDocument
    {
        // The regular expression to match input against (zero or more digits)
        private final static Pattern DIGITS = Pattern.compile("\\d*");
 
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
        {
            // Only insert the text if it matches the regular expression
            if (str != null && DIGITS.matcher(str).matches())
                super.insertString(offs, str, a);
        }
    }
    
    
    private class NumericAndLengthFilter extends DocumentFilter {

        /**
         * Number of characters allowed.
         */
        private int length = 0;

        /**
         * Restricts the number of charcacters can be entered by given length.
         * @param length Number of characters allowed.
         */
        public NumericAndLengthFilter(int length) {
            this.length = length;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                AttributeSet attr) throws
                BadLocationException {
            if (isNumeric(string)) {
                if (this.length > 0 && fb.getDocument().getLength() + string.
                        length()
                        > this.length) {
                    return;
                }
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                AttributeSet attrs) throws
                BadLocationException {
            if (isNumeric(text)) {
                if (this.length > 0 && fb.getDocument().getLength() + text.
                        length()
                        > this.length) {
                    return;
                }
                super.insertString(fb, offset, text, attrs);
            }
        }

        /**
         * This method tests whether given text can be represented as number.
         * This method can be enhanced further for specific needs.
         * @param text Input text.
         * @return {@code true} if given string can be converted to number; otherwise returns {@code false}.
         */
        private boolean isNumeric(String text) {
            if (text == null || text.trim().equals("")) {
                return false;
            }
            for (int iCount = 0; iCount < text.length(); iCount++) {
                if (!Character.isDigit(text.charAt(iCount))) {
                    return false;
                }
            }
            return true;
        }
    }
}

