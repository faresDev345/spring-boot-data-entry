package com.app.data.entry.comun;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class Utiles {

 
	
	public static Resource getPropertyFile(ResourceLoader resourceLoader, String file_path) throws IOException {
		return resourceLoader.getResource(file_path);
	}

	public static String getMonthFromFileName(String file_path) {

		String mois = "";
		if (file_path != null && file_path.length() > 0) {
			int lastIndexOfDot = file_path.lastIndexOf('.');
			//String fileExtension = file_path.substring(lastIndexOfDot + 1);
			String fileName = file_path.substring(0, lastIndexOfDot);
			mois = fileName.substring(fileName.length() - 2, fileName.length());
			// annee = fileName.substring (fileName.length()-6,fileName.length()-2);
		}
		return mois;
	}

	public static String getYearFromFileName(String file_path) {
	
		String annee = "";
		if (file_path != null && file_path.length() > 0) {
			int lastIndexOfDot = file_path.lastIndexOf('.');
			//String fileExtension = file_path.substring(lastIndexOfDot + 1);
			String fileName = file_path.substring(0, lastIndexOfDot);
			// String mois = fileName.substring (fileName.length()-2,fileName.length());
			if (fileName != null && fileName.length() > 0)
				annee = fileName.substring(fileName.length() - 6, fileName.length() - 2);
		}
		return annee;
	}	


	public final static String DEFAULT_FORMAT = "dd/MM/yyyy";
	public final static String DEFAULT_FORMAT_TGR = "ddMMyyyy";
	public static String  formatedStringDate(String lDate) {
		SimpleDateFormat formatTGR = new SimpleDateFormat(DEFAULT_FORMAT_TGR);
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		try {
			return formatter.format(formatTGR.parse(lDate));
		} catch (ParseException e) {
		    System.out.println("Erreur lors du parsing de la date : " +lDate);
			e.printStackTrace();
		}
		return lDate;
		
	}
	public static String  aujourdhui() { 
		String str ="";
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		Date date = new Date();
		
		return   formatter.format(date);
		
	}
	public static Date currentDate() {
		 Date currentDate = new Date(); 
		return  currentDate;
	}

	public static Date stringToDate(String lDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		try {
			return   formatter.parse(lDate);
		} catch (ParseException e) {
		    System.out.println("Erreur lors du parsing de la date : " +lDate);
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isNotEmptyString(String str) {
		return (str != null && !"".equals(str.trim()));
	}
	
	public static boolean isNotEmptyList(List lst) {
		if (lst == null || lst.size() == 0)
			return false;
		return true;
	}	
	
	public static boolean isNotNull(Object obj) {
		 
		return (obj != null);
	}

	public static boolean isNull(Object obj) {
		return (obj == null  );  
	}
 
	 
	// ********************
	private static final String CARACTERES_A_SUPPRIMER = "[\\p{Punct}] -'";


	 
	public static String supprimerCaracteresSpeciaux(String chaine) {
        // On définit un pattern pour les caractères à supprimer (ici, espaces et tirets)
        String pattern = "[\\s-']";
        return Pattern.compile(pattern).matcher(chaine).replaceAll("");
    }


    
    
    public static boolean areWordsEqual(String word1, String word2) {
        String normalizedWord1 = normalizeWord(word1);
        String normalizedWord2 = normalizeWord(word2);
           return normalizedWord1.equalsIgnoreCase(normalizedWord2); 
    }

    private static String normalizeWord(String word) {
        // Implement your custom normalization logic here
    //	 System.out.println(" >>>>>>Traitement des noms  normalizeWord  "+word + " ### " +word.toLowerCase().replaceAll("["+ CARACTERES_A_SUPPRIMER+"]", "")); // Output: true
        // For example, you could remove punctuation, convert to lowercase, etc.
       // return word.toLowerCase().replaceAll("[\\p{Punct}]", "");
            return word.replaceAll("[" + CARACTERES_A_SUPPRIMER + "]", "");
    }

    private static final String VOYELLE = "aeiouyAEIOUY";

    public static boolean sontSimilaires(String mot1, String mot2) {
    	   return areTheSame(mot1, mot2, 0, 0, 1);
    }

    private static boolean areTheSame(String mot1, String mot2, int i, int j, int omissionsRestantes) {
        // Cas de base : les deux mots sont vides
        if (i == mot1.length() && j == mot2.length()) {
            return true;
        }

        // Cas de base : un mot est vide et l'autre non
        if (i == mot1.length() || j == mot2.length()) {
            return false;
        }

        // Les lettres actuelles sont égales ou l'une est une voyelle et on a encore des omissions
        if (mot1.charAt(i) == mot2.charAt(j) || (VOYELLE.indexOf(mot1.charAt(i)) >= 0 || VOYELLE.indexOf(mot2.charAt(j)) >= 0) && omissionsRestantes > 0) {
            return areTheSame(mot1, mot2, i + 1, j + 1, omissionsRestantes - 1);
        } else {
            // On essaie d'omettre une lettre dans le premier mot
            boolean resultat = areTheSame(mot1, mot2, i + 1, j, omissionsRestantes);
            // Si cela ne fonctionne pas, on essaie d'omettre une lettre dans le deuxième mot
            if (!resultat) {
                resultat = areTheSame(mot1, mot2, i, j + 1, omissionsRestantes);
            }
           if( resultat )     	 System.out.println(" >>>>>>test equal passed for nouns  normalizeWord  "+mot1 + " ### " +mot2);
            return resultat;
        }
    }
	
	
	

}
