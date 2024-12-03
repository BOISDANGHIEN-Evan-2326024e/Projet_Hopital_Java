package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Colonie {

    private List<Meute> listeMeutes; 
    private Random random; 
    
    public Colonie() {
        this.listeMeutes = new ArrayList<>();
        this.random = new Random();
    }


    public void ajouterMeute(Meute meute) {
        listeMeutes.add(meute);
    }

    public void afficherLycanthropes() {
        System.out.println("===== Lycanthropes dans la colonie =====");
        for (Meute meute : listeMeutes) {
            System.out.println("Meute :");
            meute.afficherCaracteristiques();
        }
    }


    public void gestionColonie() {
        System.out.println("Début de la simulation de la colonie...");
        Scanner scanner = new Scanner(System.in);
        Meute meuteTest=new Meute("meuteTest");
    	Lycanthrope lycan1=new Lycanthrope("male","Bernard");
    	Lycanthrope lycan2=new Lycanthrope("femelle","Bianca");
    	Lycanthrope lycan3=new Lycanthrope("male","Christope");
    	Lycanthrope lycan4=new Lycanthrope("male","Louis");
    	Lycanthrope lycan5=new Lycanthrope("femelle","Lola");
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
            	
            	System.out.println("\nNous sommes au jour "+compteurJour+" !");
            	Thread.sleep(1000);
            	System.out.println("\nIl y a actuellement "+this.listeMeutes.size()+" meute/s,Voulez vous en créer une nouvelle ? \n meute : Créer une nouvelle meute \n Entrée : Passer à l'étape d'aprés");
            	String commande2 = scanner.nextLine();
            	if(commande2.equals("meute")) {
            		System.out.println("\nDonnez le nom de la meute ?");
                	String commandeee = scanner.nextLine();
            		creerNouvelleMeute(commandeee);
            	}
            	System.out.println("\nCréer Lycanthrope ?\n lycan : oui crée un Lycanthrope\n non : Ne pas crée de Lycanthrope.");
            	String commandeee1 = scanner.nextLine();
            	while(!commandeee1.equals("non")) {
            		if(commandeee1.equals("lycan")) {
                		System.out.println("\nDonnez le nom du lycan");
                		String commandeee2 = scanner.nextLine();
                		Lycanthrope lycan;
                		if(random.nextInt(3)==1) {
                			lycan=new Lycanthrope("femelle",commandeee2);
                		}
                		else {
                			lycan=new Lycanthrope("male",commandeee2);
                		}
                		lycan.afficherCaracteristiques();
                		System.out.println("\nVoulez vous associez le lycanthrope a la meute ?\n oui : Associé le Lycanthrope a une meute \n Entrée : passée cette étape");
                    	String commandeee3 = scanner.nextLine();
                    	if(commandeee3.equals("oui")){
                    		System.out.println("\nVoici les meutes disponibles :");
                    		for(int k=0;k<this.listeMeutes.size();k++) {
                            	System.out.println(listeMeutes.get(k).getNomMeute());
                            }
                    		System.out.println("\nTapez le nom de la meute a qui vous souhaitez l'associé : ");
                    		String commandeee4 = scanner.nextLine();
                    		for(int k=0;k<this.listeMeutes.size();k++) {
                            	if(listeMeutes.get(k).getNomMeute().equals(commandeee4)) {
                            		listeMeutes.get(k).ajouterLycanthrope(lycan);
                            		lycan.setMeute(listeMeutes.get(k));
                            	}
                            }
                    	}
                	}
            		else {
            			System.out.println("\nCommande inconnue.");
            		}
            		System.out.println("\nVoulez vous créer un autre lycanthrope ?\n lycan : Crée un nouveau lycanthrope \n non : Ne pas crée de lycanthrope et passé à l'étape d'aprés");
                	commandeee1 = scanner.nextLine();
            	}
            	
            	Thread.sleep(1000);

            	evoluerForce();
            	
            	Thread.sleep(1000);
            	
            	faireVieillirLycanthropes();
            	
            	Thread.sleep(1000);
            	
            	majHierarchie();
            	
            	Thread.sleep(1000);
            	
            	for(Meute meute : listeMeutes) {
            		meute.afficherHierarchie();
            		Thread.sleep(5000);
            	}
            	
            	Thread.sleep(1000);
            	
            	creeMajCouple();
            	
            	
                Thread.sleep(1000);
                
                if (estSaisonAmours()) {
                    lancerReproduction();
                    Thread.sleep(5000);
                }

                Thread.sleep(1000);
                
                genererHurlementsAleatoires();

                Thread.sleep(1000);
                
                transformerLycanthropes();
                
                Thread.sleep(1000);
                
                System.out.println("\n Que voulez faire :\n C : Consulter ou modifier une meute\n Suite : Passez au jour suivant");
                String commande = scanner.nextLine();
                
                while(!commande.equals("Suite")) {
                	if(commande.equals("C")) {
                        System.out.println("\nTapez le nom de la meute que vous souhaitez consulter :");
                        for(int k=0;k<this.listeMeutes.size();k++) {
                        	System.out.println(listeMeutes.get(k).getNomMeute());
                        }
                        String nom = scanner.nextLine();
                        for(int k=0;k<this.listeMeutes.size();k++) {
                        	if(listeMeutes.get(k).getNomMeute().equals(nom)) {
                        		Meute meuteEnCours=listeMeutes.get(k);
                        		System.out.println("\nC : Si vous souhaitez consulter les statistiques de la meute \nL : Si vous voulez consulter les caracteristiques d'un loup \nD : Supprimer un lycanthrope de la meute \nR : Pour faire retour  ");
                        		String nom1 = scanner.nextLine();
                        		if(nom1.equals("C")) {
                        			meuteEnCours.afficherCaracteristiques();
                        		}
                        		if(nom1.equals("L")) {
                        			System.out.println("\nQuel loup souhaitez vous consulter ses caractéristiques ?");
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				System.out.println(meuteEnCours.getLycanthropes().get(i).getNom());
                        			}
                        			String commande23 = scanner.nextLine();
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				if(meuteEnCours.getLycanthropes().get(i).getNom().equals(commande23)) {
                        					System.out.println("===== Caractéristiques du lycanthrope =====");
                        					meuteEnCours.getLycanthropes().get(i).afficherCaracteristiques();
                        				}
                        			}
                        		}
                        		if(nom1.equals("D")) {
                        			System.out.println("\nQuel loup souhaitez vous supprimez de la meute ?");
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				System.out.println(meuteEnCours.getLycanthropes().get(i).getNom());
                        			}
                        			String commande23 = scanner.nextLine();
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				if(meuteEnCours.getLycanthropes().get(i).getNom().equals(commande23)) {
                        					System.out.println("\n"+commande23+"a été supprimé de la meute : "+meuteEnCours.getNomMeute());
                        					meuteEnCours.enleverLycanthrope(meuteEnCours.getLycanthropes().get(i));
                        				}
                        			}
                        			
                        		}
                        		if(nom1.equals("R")) {
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
                	System.out.println("\n Que voulez faire :\n C : Consulter ou modifier une meute\n Suite : Passez au jour suivant");
                    commande = scanner.nextLine();
                }
                compteurJour+=1;
                System.out.println("\n Nous passons au jour d'aprés.");
            } catch (InterruptedException e) {
                System.out.println("Simulation interrompue.");
                break;
            }
        }
    }

    //Créer un couple automatiquement si il n'y en a pas ou mets a jour les couples des meutes si il y a un nouveau male alpha.
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
    
    //Fais évoluer aléatoirement la force des lycanthropes
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
    
    // Déterminer si une nouvelle meute doit être créée
    private boolean doitCreerNouvelleMeute() {
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

    // Créer une nouvelle meute
    private void creerNouvelleMeute(String nomMeute) {
        Meute nouvelleMeute = new Meute(nomMeute);
        System.out.println("\nUne nouvelle meute a été créée !");
        listeMeutes.add(nouvelleMeute);
    }

    // Déterminer si c'est la saison des amours
    private boolean estSaisonAmours() {
        // 10% de chance que ce soit la saison des amours
        return random.nextInt(100) < 50;
    }

    // Lancer la reproduction dans toutes les meutes
    private void lancerReproduction() {
        System.out.println("\nC'est la saison des amours !");
        for (Meute meute : listeMeutes) {
            meute.reproduction();
        }
    }

    // Faire vieillir certains lycanthropes
    private void faireVieillirLycanthropes() {
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

    // Générer des hurlements aléatoires entre certains lycanthropes
    private void genererHurlementsAleatoires() {
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

    // Transformer quelques lycanthropes en humains
    private void transformerLycanthropes() {
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
}
