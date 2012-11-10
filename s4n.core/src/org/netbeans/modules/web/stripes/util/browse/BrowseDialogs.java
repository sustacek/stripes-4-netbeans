/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.util.browse;

import org.netbeans.modules.web.stripes.palette.*;
import java.awt.Component;
import java.io.File;
import java.io.FileFilter;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * Helper class to gain access to various Browse dialogs.
 * 
 * @author Josef Sustacek
 */
public class BrowseDialogs {

    private BrowseDialogs() {
    }
    
    /**
     * Raises enum selection dialog and returns selected enum's FQN.
     * 
     * @param project
     * @param parent
     * @return
     */
    public static String enumsDialog(Project project, Component parent){
        return sourcesDialog(project, parent, new EnumsFilter());
    }
    
    /**
     * Raises Action Bean Context selection dialog and returns selected class' FQN.
     * 
     * @param project
     * @param parent
     * @return
     */
    public static String actionBeanContextsDilaog(Project project, Component parent){
        return sourcesDialog(project, parent, new ActionBeanContextsFilter());
    }
    
    /**
     * Returns the absolute path to selected image file or empty string, in none selected.
     * Supported 
     * 
     * @param target the component, which is in the same project as the files that are browsed
     * @param parent the component in which the dialog shoud be raised
     * 
     * @return the absolute path to selected jsp file or empty string
     */
    public static String imageSourcesDialog(Project project, Component parent, String[] exts){
        return docrootSourcesDialog(project, parent, new ImagesFilter(exts));
    }
    
    /**
     * Returns the fully qualified name of  action bean class selected via raised dialog.
     * 
     * @param target the component, which is in the same project as the files that are browsed
     * @param parent the component in which the dialog shoud be raised
     * @return
     */
    public static String actionBeansDialog(Project project, Component parent){
        return sourcesDialog(project, parent, new ActionBeansFilter());
    }
    
    
    /**
     * Returns the fully qualified name of class selected via raised dialog.
     * 
     * @param target the component, which is in the same project as the files that are browsed
     * @param parent the component in which the dialog shoud be raised
     * @return
     */
    public static String javaSourcesDialog(Project project, Component parent){
        return sourcesDialog(project, parent, new JavaSourcesFilter());
    }
    
    
    /**
     * Returns the fully qualified class name of a class writen in file 
     * selected via raised dialog.
     * 
     * @param target the component, which is in the same project as the files that are browsed
     * @param parent the component in which the dialog shoud be raised
     * @param filter file filter to be applied on files
     * @return
     */
    public static String sourcesDialog(Project project, Component parent, FileFilter filter){
        if (null == filter) filter = new AcceptingFilter();
        
        SourceGroup[] sourceGroups = 
                StripesPaletteUtilities.getSourceGroups(project, 
                    JavaProjectConstants.SOURCES_TYPE_JAVA);

        java.io.File file = null;
        if (sourceGroups.length > 0) {
            FileObject fo = BrowseFolders.showDialog(sourceGroups, filter);

            if (fo != null)
                file = FileUtil.toFile(fo);
        }
        else {
            JFileChooser ch = new JFileChooser(FileUtil.toFile(project.getProjectDirectory()));
            int returnVal = ch.showOpenDialog(parent);

            if (returnVal == JFileChooser.APPROVE_OPTION)
                file = ch.getSelectedFile();
        }

        if (file != null) {
            return getJavaClassFQN(file, sourceGroups);
        }
        
        return "";
    }
    
    /**
     * Raises file selection dialog for docroot files (jsp, html, css...) 
     * and returns selected file's absolute path from its 
     * docroot (/WEB-INF/jsp/file.jsp).
     * 
     * @param project
     * @param parent
     * @param filter
     * @return
     */
    public static String docrootSourcesDialog(Project project, Component parent, FileFilter filter){
        if (null == filter) filter = new AcceptingFilter();
        
        SourceGroup[] sourceGroups = 
                StripesPaletteUtilities.getSourceGroups(project, 
                    StripesPaletteUtilities.DOC_SOURCES);

        java.io.File file = null;
        if (sourceGroups.length > 0) {
            FileObject fo = BrowseFolders.showDialog(sourceGroups, filter);
            if (fo != null)
                file = FileUtil.toFile(fo);
        }
        else {
            JFileChooser ch = new JFileChooser(FileUtil.toFile(project.getProjectDirectory()));
                int returnVal = ch.showOpenDialog(parent);

            if (returnVal == JFileChooser.APPROVE_OPTION)
                file = ch.getSelectedFile();
        }

        if (file != null) {
            return getJspFileURL(file, sourceGroups);
        }

        return "";
    }
    
    /**
     * Returns the absolute path to selected jsp file or empty string, in none selected.
     * 
     * @param target the component, which is in the same project as the files that are browsed
     * @param parent the component in which the dialog shoud be raised
     * 
     * @return the absolute path to selected jsp file or empty string
     */
    public static String jspSourcesDialog(Project project, Component parent){
        return docrootSourcesDialog(project, parent, new JspFilesFilter());
    }
    
    /**
     * Returns fully qualified name of class included in given file based 
     * on given source groups;
     * 
     * @param file
     * @param sourceGroups
     * @return
     */
    private static String getJavaClassFQN(File file, SourceGroup[] sourceGroups){
        String classFQN = file.getAbsolutePath();
        FileObject classFO = FileUtil.toFileObject(file);
        try {
            String absPathToFile = getAbsolutePathFromRoot(sourceGroups, classFO);

            // TODO another way to get FQN of class in given file?
            absPathToFile = absPathToFile.substring(1); // remove first slash
            absPathToFile = absPathToFile.replaceAll("/", "."); // convert to "com.my.Class"
            absPathToFile = absPathToFile.substring(
                    0, absPathToFile.length() - ".java".length()); // remove extension

            if (absPathToFile.length() > 0)
                classFQN = absPathToFile;
        } catch (Exception e) {
            // eventual exceptions imply the absolute path to be used
        }

        return classFQN;
    }
    
    /**
     * Finds given file in given source groups and then computes the absolute 
     * path from the given source group root.
     * 
     * @param file
     * @param sourceGroups
     * @return
     */
    private static String getJspFileURL(File file, SourceGroup[] sourceGroups) {
        String path = file.getAbsolutePath();
        FileObject aFO = FileUtil.toFileObject(file);
        try {
            String absPathToFile = getAbsolutePathFromRoot(sourceGroups, aFO);
            if (absPathToFile.length() > 0)
                path = absPathToFile;
        }
        catch (Exception e) {
            //eventual exceptions imply the absolute path to be used
        }

        return path;
    }
    
    private static String getRelativePath(FileObject base, FileObject target) {
        
        final String DELIM = "/";
        final String PARENT = ".." + DELIM;
        
        String targetPath = target.getPath();
        String basePath = base.getPath();

        //paths begin either with '/' or with '<letter>:/' - ensure that in the latter case the <letter>s equal
        String baseDisc = basePath.substring(0, basePath.indexOf(DELIM));
        String targetDisc = targetPath.substring(0, targetPath.indexOf(DELIM));
        if (!baseDisc.equals(targetDisc))
            return ""; //different disc letters, thus returning an empty string to signalize this fact

        //cut a filename at the end taking last index for case of the same dir name as file name, really obscure but possible ;)
        basePath = basePath.substring(0, basePath.lastIndexOf(base.getNameExt()));
        targetPath = targetPath.substring(0, targetPath.lastIndexOf(target.getNameExt()));

        //iterate through prefix dirs until difference occurres
        StringTokenizer baseST = new StringTokenizer(basePath, DELIM);
        StringTokenizer targetST = new StringTokenizer(targetPath, DELIM);
        String baseDir = "";
        String targetDir = "";
        while (baseST.hasMoreTokens() && targetST.hasMoreTokens() && baseDir.equals(targetDir)) {
            baseDir = baseST.nextToken();
            targetDir = targetST.nextToken();
        }
        //create prefix consisting of parent dirs ("..")
        StringBuffer parentPrefix = new StringBuffer(!baseDir.equals(targetDir) ? PARENT : "");
        while (baseST.hasMoreTokens()) {
            parentPrefix.append(PARENT);
            baseST.nextToken();
        }
        //append remaining dirs with delimiter ("/")
        StringBuffer targetSB = new StringBuffer(!baseDir.equals(targetDir) ? targetDir + DELIM : "");
        while (targetST.hasMoreTokens())
            targetSB.append(targetST.nextToken() + DELIM);

        //resulting path
        targetPath = parentPrefix.toString() + targetSB.toString() + target.getNameExt();
        
        return targetPath;
    }
    
    private static String getAbsolutePathFromRoot(SourceGroup[] sourceGroups, FileObject target) {
        String targetPath = target.getPath();
        
        for(SourceGroup sg: sourceGroups){
            if(sg.contains(target)){
                // get path of the root folder
                String rootPath = sg.getRootFolder().getPath();
                
                // cut out the root folder path
                String path = targetPath.replaceFirst(rootPath, "");
                
                return path;
            }
        }
        
        return null;
    }
}
