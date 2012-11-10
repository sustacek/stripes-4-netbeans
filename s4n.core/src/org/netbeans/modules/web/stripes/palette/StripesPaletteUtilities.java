package org.netbeans.modules.web.stripes.palette;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JTree;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.editor.NbEditorUtilities;
// JSP parser
//import org.netbeans.modules.web.core.syntax.spi.JspContextInfo;
// JSP editor
//import org.netbeans.modules.web.jsps.parserapi.JspParserAPI;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.text.NbDocument;

/**
 *
 * @author Josef Sustacek
 */
public class StripesPaletteUtilities {
    
    public static final String DOC_SOURCES = "doc_root";
    
    /**
     * Inserts given string into given component. Teh position of the string
     * is based on current position of the caret in the text component.
     * 
     * At the end, caret is shifted based ongiven caretShift numbre. Possitive 
     * caretShift = shift forward, negative caretShift = shift backwards.
     * 
     * @param s
     * @param target
     * @param caretShift
     * @throws javax.swing.text.BadLocationException
     */
    public static void insert(final String s, final JTextComponent target, 
            final int caretShift) throws BadLocationException {

        final StyledDocument doc = (StyledDocument) target.getDocument();

        class AtomicChange implements Runnable {

            public void run() {
                Document value = target.getDocument();
                if (value == null) {
                    return;
                }
                try {
                    insert(s, target, doc, caretShift);
                } catch (BadLocationException e) {
                }
            }
        }

        try {
            NbDocument.runAtomicAsUser(doc, new AtomicChange());
        } catch (BadLocationException ex) {
        }

    }

    private static int insert(String s, JTextComponent target, 
            Document doc, int caretShift) throws BadLocationException {

        int start = -1;

        try {

            //firstly, find selected text range:
            Caret caret = target.getCaret();
            int p0 = Math.min(caret.getDot(), caret.getMark());
            int p1 = Math.max(caret.getDot(), caret.getMark());
            doc.remove(p0, p1 - p0);

            //then, replace selected text with the inserted one:
            start = caret.getDot();
            doc.insertString(start, s, null);
            
            caret.setDot(caret.getDot() + caretShift);

        } catch (BadLocationException ble) {
        }

        return start;

    }
    
    /**
     * Returns source groups of given type and from the given project.
     * 
     * @param project
     * @param sourceType
     * @return
     */
    public static SourceGroup[] getSourceGroups(Project project, String sourceType) {
    
        SourceGroup[] sg = new SourceGroup[] {};
        if(null != project){
            Sources sources = ProjectUtils.getSources(project);
            sg = sources.getSourceGroups(sourceType);
        }
        
        return sg;
    }
    
    /**
     * Returns source groups of given type and from the project, in which the given 
     * file is located.
     * 
     * @param fObj
     * @return
     */
    public static SourceGroup[] getSourceGroups(FileObject fObj, String sourceType) {
        Project project = FileOwnerQuery.getOwner(fObj);
        
        return getSourceGroups(project, sourceType);
    }
    
    
    /**
     * Returns the current position of the caret from the beginning 
     * of the current line.
     * 
     * @param component
     * @return
     */
    public static int getCaretIndent(JTextComponent component) {
        Caret caret = component.getCaret();
        int caretPosition = Math.min(caret.getDot(), caret.getMark());
        
        Element root = component.getDocument().getDefaultRootElement();
        int line = root.getElementIndex(caretPosition);
        int lineStart = root.getElement(line).getStartOffset();

        return caretPosition - lineStart;
    }
    
    /**
     * Retrieves the currently used Stripes taglib prefix used in given 
     * text component.
     * 
     * @param target
     * @return
     */
    public static String findStripesPrefix(JTextComponent target) {
        String res = getTagLibPrefix(target, CodeSnippet.STRIPES_PREFIX);
//        if (res == null)
//            insertTagLibRef(target, CodeSnippet.STRIPES_PREFIX, CodeSnippet.STRIPES_URI);
        return (res != null) ? res : CodeSnippet.STRIPES_PREFIX;
    }
    
    // TODO from not-friend modules - JPS editor & JSP parser
    public static String getTagLibPrefix(JTextComponent target, String tagLibUri) {
        FileObject fobj = getFileObject(target);
        if (fobj != null) {
//            JspParserAPI.ParseResult result = null;
                    //JspContextInfo.getContextInfo(fobj).getCachedParseResult(target.getDocument(), fobj, false, true);
//            if (result != null && result.getPageInfo() != null) {
//                 for (TagLibraryInfo tli : result.getPageInfo().getTaglibs()) {
//                     if (tagLibUri.equals(tli.getURI()))
//                         return tli.getPrefixString();
//                 }
//            }
        }
        return null;
    }
    
    private static FileObject getFileObject(JTextComponent target) {
        BaseDocument doc = (BaseDocument) target.getDocument();
        DataObject dobj = NbEditorUtilities.getDataObject(doc);
        FileObject fobj = (dobj != null) ? NbEditorUtilities.getDataObject(doc).getPrimaryFile() : null;
        return fobj;
    }

    /*
    private static void insertTagLibRef(JTextComponent target, String prefix, String uri) {
        Document doc = target.getDocument();
        if (doc != null && doc instanceof BaseDocument) {
            BaseDocument baseDoc = (BaseDocument)doc;
            baseDoc.atomicLock();
            try {
                int pos = 0;  // FIXME: compute better where to insert tag lib definition?
                String definition = "<%@taglib prefix=\""+prefix+"\" uri=\""+uri+"\"%>\n";  //NOI18N
                
                //test for .jspx. FIXME: find better way to detect xml syntax?.
                FileObject fobj = getFileObject(target);
                if (fobj != null && "jspx".equals(fobj.getExt())) {
                    int baseDocLength = baseDoc.getLength();
                    String text = baseDoc.getText(0, baseDocLength);
                    String jspRootBegin = "<jsp:root "; //NOI18N
                    int jspRootIndex = text.indexOf(jspRootBegin);
                    if (jspRootIndex != -1) {
                        pos = jspRootIndex + jspRootBegin.length();
                        definition = "xmlns:" + prefix + "=\"" + uri + "\" ";  //NOI18N
                    }
                }

                doc.insertString(pos, definition, null);
            }
            catch (BadLocationException e) {
                Exceptions.printStackTrace(e);
            }
            finally {
                baseDoc.atomicUnlock();
            }
        }
    }
    */

    /**
     * Retrieves the first found JTree compoenent as descendant of given 
     * component. 
     * 
     * Runs as BFS - Breadth-first search.
     * 
     * @param component
     * @return
     */
    public static JTree findTreeComponent(Component component) {
        if (component instanceof JTree) {
            return (JTree) component;
        }
        if (component instanceof Container) {
            Component[] components = ((Container) component).getComponents();
            for (int i = 0; i < components.length; i++) {
                JTree tree = findTreeComponent(components[i]);
                if (tree != null) {
                    return tree;
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves the project, in which the given text compoenent
     * is located.
     * 
     * @param textComponent
     * @return
     */
    public static Project getProject(JTextComponent textComponent){
        Document targetDoc = textComponent.getDocument();
        FileObject targetDocFO = NbEditorUtilities.getFileObject(targetDoc);
        
        return FileOwnerQuery.getOwner(targetDocFO);
    }
    
}