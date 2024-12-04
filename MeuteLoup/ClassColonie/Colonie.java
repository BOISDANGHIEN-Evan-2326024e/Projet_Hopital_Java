package ClassColonie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Model.TextColor;

public class Colonie {

    private List<Meute> listeMeutes; 
    private Random random; 
    
    /**
     * 
     */
    public Colonie() {
        this.listeMeutes = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Ajouter une meute a la colonie
     * @param meute
     */
    public void ajouterMeute(Meute meute) {
        listeMeutes.add(meute);
    }

    /**
     * Afficher les lycanthropes de la colonie
     */
    public void afficherLycanthropes() {
        System.out.println("===== Lycanthropes dans la colonie =====");
        for (Meute meute : listeMeutes) {
            System.out.println("Meute :");
            meute.afficherCaracteristiques();
        }
    }

    /**
     * Méthode qui gere la colonie avec des évenements
     */
    public void gestionColonie() {
        System.out.println(TextColor.BLUE_BOLD + "Début de la simulation ..." + TextColor.RESET);
        Scanner scanner = new Scanner(System.in);
        Meute meuteTest=new Meute("meuteTest");
    	Lycanthrope lycan1=new Lycanthrope("male","Bernard");
    	Lycanthrope lycan2=new Lycanthrope("femelle","Bianca");
    	Lycanthrope lycan3=new Lycanthrope("male","Christope");
    	Lycanthrope lycan4=new Lycanthrope("male","Lucas");
    	Lycanthrope lycan5=new Lycanthrope("femelle","Coline");
    	Lycanthrope lycan6=new Lycanthrope("male","Tom");
    	Lycanthrope lycan7=new Lycanthrope("male","Enzo");
    	Lycanthrope lycan8=new Lycanthrope("femelle","Cecile");
    	Lycanthrope lycan9=new Lycanthrope("male","Evan");
    	meuteTest.ajouterLycanthrope(lycan1);
    	meuteTest.ajouterLycanthrope(lycan2);
    	meuteTest.ajouterLycanthrope(lycan3);
    	meuteTest.ajouterLycanthrope(lycan4);
    	meuteTest.ajouterLycanthrope(lycan5);
    	meuteTest.ajouterLycanthrope(lycan6);
    	meuteTest.ajouterLycanthrope(lycan7);
    	meuteTest.ajouterLycanthrope(lycan8);
    	meuteTest.ajouterLycanthrope(lycan9);
    	this.ajouterMeute(meuteTest);
        int compteurJour=1;
        while (true) { 
            try {
            	
            	Thread.sleep(10000); 
            	
            	System.out.println("\nNous sommes au "+TextColor.BRIGHT_YELLOW+"jour "+compteurJour+" !"+TextColor.RESET);
            	
            	Thread.sleep(1000);
            	
            	System.out.println(TextColor.CYAN_BOLD+"\nIl y a actuellement "+this.listeMeutes.size()+" meute/s,Voulez vous en créer une nouvelle ?"+TextColor.RESET+"\n  "+TextColor.BRIGHT_CYAN+"meute"+TextColor.RESET+" : Créer une nouvelle meute \n  "+TextColor.BRIGHT_CYAN+"Entrée "+TextColor.RESET+": Passer à l'étape d'aprés\n");
            	String reponse1 = scanner.nextLine();
            	if(reponse1.equals("meute")) {
            		System.out.println("\nDonnez le nom de la meute ?");
                	String reponse2 = scanner.nextLine();
            		creerNouvelleMeute(reponse2);
            	}
            	System.out.println(TextColor.CYAN_BOLD+"\nCréer Lycanthrope ?\n"+TextColor.RESET+ "  "+TextColor.BRIGHT_CYAN+"lycan"+TextColor.RESET +": oui crée un Lycanthrope\n  "+TextColor.BRIGHT_CYAN+"non"+TextColor.RESET +" : Ne pas crée de Lycanthrope.");
            	
            	String reponse3 = scanner.nextLine();
            	
            	while(!reponse3.equals("non")) {
            		if(reponse3.equals("lycan")) {
                		System.out.println("\nDonnez le nom du lycan");
                		String reponse4 = scanner.nextLine();
                		Lycanthrope lycan;
                		if(random.nextInt(3)==1) {
                			lycan=new Lycanthrope("femelle",reponse4);
                		}
                		else {
                			lycan=new Lycanthrope("male",reponse4);
                		}
                		lycan.afficherCaracteristiques();
                		
                		System.out.println(TextColor.CYAN_BOLD+"\nVoulez vous associez le lycanthrope a la meute ?\n"+TextColor.RESET+ "  "+TextColor.BRIGHT_CYAN+"oui "+TextColor.RESET+": Associé le Lycanthrope a une meute \n  "+TextColor.BRIGHT_CYAN+"Entrée "+TextColor.RESET+": passée cette étape");
                    	
                		String reponse5 = scanner.nextLine();
                    	if(reponse5.equals("oui")){
                    		System.out.println("\nVoici les meutes disponibles :\n");
                    		for(int k=0;k<this.listeMeutes.size();k++) {
                            	System.out.println("  🫂​ "+TextColor.BRIGHT_MAGENTA+listeMeutes.get(k).getNomMeute()+TextColor.RESET+" [ Membres actuelles : "+listeMeutes.get(k).getLycanthropes().size()+" ]"+"\n");
                            }
                    		
                    		System.out.println("\nTapez le nom de la meute a qui vous souhaitez l'associé :");
                    		
                    		String reponse6 = scanner.nextLine();
                    		
                    		for(int k=0;k<this.listeMeutes.size();k++) {
                            	if(listeMeutes.get(k).getNomMeute().equals(reponse6)) {
                            		listeMeutes.get(k).ajouterLycanthrope(lycan);
                            		lycan.setMeute(listeMeutes.get(k));
                            	}
                            }
                    	}
                	}
            		else {
            			System.out.println("\nCommande inconnue.");
            		}
            		System.out.println(TextColor.CYAN_BOLD+"\nVoulez vous créer un autre lycanthrope ?\n"+TextColor.RESET +"  "+TextColor.BRIGHT_CYAN+"lycan "+TextColor.RESET+": Crée un nouveau lycanthrope \n  "+TextColor.BRIGHT_CYAN+"non "+TextColor.RESET+": Ne pas crée de lycanthrope et passé à l'étape d'aprés");
            		reponse3 = scanner.nextLine();
            	}
            	
            	Thread.sleep(1000);

            	evoluerForce();
            	
            	Thread.sleep(1000);
            	
            	faireVieillirLycanthropes();
            	
            	Thread.sleep(3000);
            	
            	majHierarchie();
            	
            	Thread.sleep(3000);
            	
            	for(Meute meute : listeMeutes) {
            		meute.afficherHierarchie();
            		Thread.sleep(5000);
            	}
            	
            	Thread.sleep(3000);
            	
            	creeMajCouple();
            	
            	
                Thread.sleep(3000);
                
                if (estSaisonAmours()) {
                    lancerReproduction();
                    Thread.sleep(5000);
                }

                Thread.sleep(3000);
                
                genererHurlementsAleatoires();

                Thread.sleep(3000);
                
                transformerLycanthropes();
                
                Thread.sleep(3000);
                
                System.out.println(TextColor.CYAN_BOLD+"\n Que voulez faire :\n"+ TextColor.RESET+"  "+TextColor.BRIGHT_CYAN+"C"+TextColor.RESET+" : Consulter ou modifier une meute\n  "+TextColor.BRIGHT_CYAN+"Suite"+TextColor.RESET+" : Passez au jour suivant");
                String nouvelleReponse1 = scanner.nextLine();
                
                while(!nouvelleReponse1.equals("Suite")) {
                	if(nouvelleReponse1.equals("C")) {
                        System.out.println("\nTapez le nom de la meute que vous souhaitez consulter :\n");
                        for(int k=0;k<this.listeMeutes.size();k++) {
                        	System.out.println("  🫂​ "+TextColor.BRIGHT_MAGENTA+listeMeutes.get(k).getNomMeute()+TextColor.RESET+" [ Membres actuelles : "+listeMeutes.get(k).getLycanthropes().size()+" ]"+"\n");
                        }
                        String nouvelleReponse2 = scanner.nextLine();
                        for(int k=0;k<this.listeMeutes.size();k++) {
                        	if(listeMeutes.get(k).getNomMeute().equals(nouvelleReponse2)) {
                        		Meute meuteEnCours=listeMeutes.get(k);
                        		System.out.println("\n  "+TextColor.BRIGHT_CYAN+"C"+TextColor.RESET+" : Si vous souhaitez consulter les statistiques de la meute \n  "+TextColor.BRIGHT_CYAN+"L"+TextColor.RESET+" : Si vous voulez consulter les caracteristiques d'un loup \n  "+TextColor.BRIGHT_CYAN+"D"+TextColor.RESET+" : Supprimer un lycanthrope de la meute \n  "+TextColor.BRIGHT_CYAN+"R"+TextColor.RESET+" : Pour faire retour  ");
                        		String nouvelleReponse3 = scanner.nextLine();
                        		if(nouvelleReponse3.equals("C")) {
                        			meuteEnCours.afficherCaracteristiques();
                        		}
                        		if(nouvelleReponse3.equals("L")) {
                        			System.out.println("\nQuel loup souhaitez vous consulter :\n");
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				System.out.println("  🐺 "+TextColor.RED+meuteEnCours.getLycanthropes().get(i).getNom()+"\n"+TextColor.RESET);
                        			}
                        			String nouvelleReponse4 = scanner.nextLine();
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				if(meuteEnCours.getLycanthropes().get(i).getNom().equals(nouvelleReponse4)) {
                        					System.out.println("===== Caractéristiques du lycanthrope =====");
                        					meuteEnCours.getLycanthropes().get(i).afficherCaracteristiques();
                        				}
                        			}
                        		}
                        		if(nouvelleReponse3.equals("D")) {
                        			System.out.println("\nQuel loup souhaitez vous supprimez de la meute ?");
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				System.out.println(meuteEnCours.getLycanthropes().get(i).getNom());
                        			}
                        			String nouvelleReponse5 = scanner.nextLine();
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				if(meuteEnCours.getLycanthropes().get(i).getNom().equals(nouvelleReponse5)) {
                        					System.out.println("\n"+nouvelleReponse5+"a été supprimé de la meute : "+meuteEnCours.getNomMeute());
                        					meuteEnCours.enleverLycanthrope(meuteEnCours.getLycanthropes().get(i));
                        				}
                        			}
                        			
                        		}
                        		if(nouvelleReponse3.equals("R")) {
                        			continue;
                        		}
                        		else{
                        			System.out.println("\nCommande inconnue");
                        		}
                        	}
                        }
                     }
                	else {
                		System.out.print("\nCommande inconnue");
                	}
                	System.out.println(TextColor.CYAN_BOLD+"\n Que voulez faire :\n"+TextColor.RESET +"  "+TextColor.BRIGHT_CYAN+"C"+TextColor.RESET+" : Consulter ou modifier une meute\n  "+TextColor.BRIGHT_CYAN+"Suite"+TextColor.RESET+" : Passez au jour suivant");
                	nouvelleReponse1 = scanner.nextLine();
                }
                compteurJour+=1;
                System.out.println("\n Nous passons au jour d'aprés.");
            } catch (InterruptedException e) {
                System.out.println("Simulation interrompue.");
                break;
            }
        }
    }

    /**
     * Créer un couple automatiquement si il n'y en a pas ou mets a jour les couples des meutes si il y a un nouveau male alpha.
     */
    public void creeMajCouple() {
    	
    	for(int k=0;k<this.listeMeutes.size();k++) {
    		Meute meuteEnCours=this.listeMeutes.get(k);
    		if(meuteEnCours.getLycanthropes().size()<3) {
    			continue;
    		}
    		if(meuteEnCours.coupleAlphaExiste()==false) {
    			Lycanthrope maleAlpha=null;
    			Lycanthrope femelleAlpha=null;
    			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
    				Lycanthrope lycan=meuteEnCours.getLycanthropes().get(i);
    				if(lycan.getSexe().equals("femelle")) {
    					if(femelleAlpha==null) {
    						femelleAlpha=lycan;
    					}
    					if(lycan.getForce()>femelleAlpha.getForce()) {
    						femelleAlpha=lycan;
    					}
    				}
    				else {
    					if(maleAlpha==null) {
    						maleAlpha=lycan;
    					}
    					if(lycan.getForce()>maleAlpha.getForce()) {
    						maleAlpha=lycan;
    					}
    				}
    			}
    			System.out.println("\nNouveau couple crée dans la meute : "+this.listeMeutes.get(k).getNomMeute()+". Avec en male alpha : "+maleAlpha.getNom()+" et en femelle Alpha : "+femelleAlpha.getNom());
    			this.listeMeutes.get(k).constituerCoupleAlpha(maleAlpha, femelleAlpha);
    		}
    		if(!meuteEnCours.getLycanthropes().contains(meuteEnCours.getCoupleAlpha().getMaleAlpha())) {
    			Lycanthrope maleAlpha=null;
    			Lycanthrope femelleAlpha=meuteEnCours.getCoupleAlpha().getFemelleAlpha();
    			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
    				Lycanthrope lycan=meuteEnCours.getLycanthropes().get(i);
    				if(maleAlpha == null) {
    					maleAlpha=lycan;
    				}
    				if((maleAlpha.getForce()>lycan.getForce() && lycan.getSexe()=="male") || maleAlpha==null) {
    					maleAlpha=lycan;
    				}
    				if(femelleAlpha.getForce()>lycan.getForce() && lycan.getSexe()=="femelle") {
    					femelleAlpha=lycan;
    				}
    			}
    				meuteEnCours.constituerCoupleAlpha(maleAlpha, femelleAlpha);
    				System.out.println("\nNouveau couple crée dans la meute : "+this.listeMeutes.get(k).getNomMeute()+". Avec en male alpha : "+maleAlpha.getNom()+" et en femelle Alpha : "+femelleAlpha.getNom());
    		}
    		if(!meuteEnCours.getLycanthropes().contains(meuteEnCours.getCoupleAlpha().getFemelleAlpha())) {
    			Lycanthrope femelleAlpha=null;
    			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
    				Lycanthrope lycan=meuteEnCours.getLycanthropes().get(i);
    				if(femelleAlpha==null) {
    					femelleAlpha=lycan;
    				}
    				if((femelleAlpha.getForce()<lycan.getForce() && lycan.getSexe()=="femelle")) {
    					femelleAlpha=lycan;
    				}
    			}
    				meuteEnCours.constituerCoupleAlpha(meuteEnCours.getCoupleAlpha().getMaleAlpha(), femelleAlpha);
    				System.out.println("\nNouveau couple crée dans la meute : "+this.listeMeutes.get(k).getNomMeute()+". Avec en male alpha : "+meuteEnCours.getCoupleAlpha().getMaleAlpha().getNom()+" et en femelle Alpha : "+femelleAlpha.getNom());
    		}
    		else {
    			Lycanthrope maleAlphaC=meuteEnCours.getCoupleAlpha().getMaleAlpha();
    			Lycanthrope maleAlpha=meuteEnCours.getCoupleAlpha().getMaleAlpha();
    			Lycanthrope femelleAlpha=meuteEnCours.getCoupleAlpha().getFemelleAlpha();
    			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
    				Lycanthrope lycan=meuteEnCours.getLycanthropes().get(i);
    				if(maleAlpha.getForce()<lycan.getForce() && lycan.getSexe()=="male") {
    					maleAlpha=lycan;
    				}
    				if(femelleAlpha.getForce()<lycan.getForce() && lycan.getSexe()=="femelle") {
    					femelleAlpha=lycan;
    				}
    			}
    			if(!maleAlpha.equals(maleAlphaC)) {
    				meuteEnCours.constituerCoupleAlpha(maleAlpha, femelleAlpha);
    				System.out.println("\nNouveau couple crée dans la meute : "+this.listeMeutes.get(k).getNomMeute()+". Avec en male alpha : "+maleAlpha.getNom()+" et en femelle Alpha : "+femelleAlpha.getNom());
    			}
    		}
    	}
    }
    
    /**
     * Fais évoluer aléatoirement la force des lycanthropes
     */
    public void evoluerForce() {
    	for (Meute meute : listeMeutes) {
            for(Lycanthrope lycan : meute.getLycanthropes()) {
            	if(random.nextInt(100) < 40) {
            		lycan.setForce(lycan.getForce()-1);
            	}
            	if(random.nextInt(100) < 40) {
            		lycan.setForce(lycan.getForce()+1);
            	}
            }
        }
    }
    
    /**
     * Déterminer si une nouvelle meute doit être créée
     * @return
     */
    public boolean doitCreerNouvelleMeute() {
        for (Meute meute : listeMeutes) {
            if (meute.getLycanthropes().size() > 12) {
                return true;
            }
        }
        if(listeMeutes.size()==0) {
        	return true;
        }
        return false;
    }

    /**
     * Créer une nouvelle meute
     * @param nomMeute
     */
    public void creerNouvelleMeute(String nomMeute) {
        Meute nouvelleMeute = new Meute(nomMeute);
        System.out.println("\nUne nouvelle meute a été créée !");
        listeMeutes.add(nouvelleMeute);
    }

    /**
     * Déterminer si c'est la saison des amours
     * @return
     */
    public boolean estSaisonAmours() {
        // 10% de chance que ce soit la saison des amours
        return random.nextInt(100) < 50;
    }

    /**
     * Lancer la reproduction dans toutes les meutes
     */
    public void lancerReproduction() {
        System.out.println("\nC'est la saison des amours !");
        for (Meute meute : listeMeutes) {
            meute.reproduction();
        }
    }

    /**
     * Faire vieillir certains lycanthropes
     */
    public void faireVieillirLycanthropes() {
        System.out.println("\nLes lycanthropes vieillissent... \n");
        for (Meute meute : listeMeutes) {
            for (Lycanthrope lycan : meute.getLycanthropes()) {
            	if(random.nextInt(100) < 20) {
                	if(lycan.getCategorieAge()=="adulte") {
                		lycan.setCategorieAge("vieux");
                		lycan.setForce(lycan.getForce()-1);
                	}
                	if(lycan.getCategorieAge()=="jeune") {
                		lycan.setCategorieAge("adulte");
                		lycan.setForce(lycan.getForce()+2);
                	}
                	else {
                		continue;
                	}
            	}
            	
            }
        }
    }

    /**
     * Générer des hurlements aléatoires entre certains lycanthropes
     */
    public void genererHurlementsAleatoires() {
        System.out.println("\nDes hurlements aléatoires résonnent dans la colonie...\n");
        for (Meute meute : listeMeutes) {
            List<Lycanthrope> lycanthropes = meute.getLycanthropes();
            if (lycanthropes.size() > 1) {
                Lycanthrope emetteur = lycanthropes.get(random.nextInt(lycanthropes.size()));
                Lycanthrope recepteur = lycanthropes.get(random.nextInt(lycanthropes.size()));
                if (!emetteur.equals(recepteur)) {
                    Hurlement hurlement = new Hurlement("domination", emetteur);
                    emetteur.hurler(hurlement);
                    recepteur.entendreHurlement(hurlement, false);// A MODIFIER
                }
            }
        }
    }

    /**
     * Transformer quelques lycanthropes en humains
     */
    public void transformerLycanthropes() {
        System.out.println("\nDes lycanthropes se transforment peut etre en humains...");
        
        for (Meute meute : listeMeutes) {
        	List<Lycanthrope> l=new ArrayList<Lycanthrope>();
            for (Lycanthrope lycan : meute.getLycanthropes()) {
                if (random.nextInt(100) < 5) {
                	l.add(lycan);
                    
                }
            }
            for(Lycanthrope lycann : l) {
            	lycann.transformation();
                System.out.println("\n"+lycann.getNom()+" se transforme !");
                meute.enleverLycanthrope(lycann);
            }
        }
        
    }
    
    /**
     * Mets a jour la hiérarchie des meutes
     */
    public void majHierarchie() {
    	for(Meute meute : listeMeutes) {
    		for(int k=0;k<meute.getLycanthropes().size();k++) {
    			Lycanthrope lycan=meute.getLycanthropes().get(k);
    			List<Lycanthrope> lDomines= new ArrayList<Lycanthrope>();
    			List<Lycanthrope> lDominance= new ArrayList<Lycanthrope>();
    			for(int i=0;i<meute.getLycanthropes().size();i++) {
    				Lycanthrope lycanTest=meute.getLycanthropes().get(i);
    				if(lycan.getForce()>meute.getLycanthropes().get(i).getForce()) {
    					lDomines.add(lycanTest);
    				}
    				if(lycan.getForce()<meute.getLycanthropes().get(i).getForce()) {
    					lDominance.add(lycanTest);
    				}
    			}
    			lycan.setListeDominance(lDominance);
    			lycan.setListeDomines(lDomines);
    			lycan.setFacteurDomination(lDomines.size()-lDominance.size());
    		}
    		meute.creerHierarchie();
    		meute.rangToRangLettre();
    	}
    	
    }

	public List<Meute> getListeMeutes() {
		return listeMeutes;
	}

	public void setListeMeutes(List<Meute> listeMeutes) {
		this.listeMeutes = listeMeutes;
	}
    
    
}
