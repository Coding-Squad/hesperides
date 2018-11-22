# Hespérides Pipeline

![alt text](CICD-resource.png)

**Continous Delivery** ==> manual deployment

## Installation du hook git de pre-commit

    cp pre-commit.hook.sh .git/hooks/pre-commit

## Description des pipelines

### Pipelines de build

- `Jenkinsfile_build_docker_image` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES/job/continuous-delivery/job/build_docker_image/)):
Pipeline de création de l'image d'[Hesperides](https://github.com/voyages-sncf-technologies/hesperides) pour publication sur le dépôt Docker interne :
récupère l'image Docker du [Dockerhub public](https://hub.docker.com/r/hesperides/hesperides/) pour la surcharger avec le certificat de l'Active Directory, et la pusher vers Artifactory.
_cf._ [docker/Dockerfile](docker/Dockerfile)

- `Jenkinsfile_build_docker_image_nigthly` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES/job/continuous-delivery/job/build_docker_image_nigthly/)):
Déclenche le job précédent toutes les nuits

- `Jenkinsfile_datamigration_build_docker_image` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES/job/data-migration/job/build_docker_image/)):
Pipeline de création de l'image d'[hesperides-data-migration pour VSCT](https://github.com/voyages-sncf-technologies/hesperides-data-migration) pour publication sur le dépôt Docker interne :
construit l'image depuis le repo GitHub public, et la pushe vers Artifactory.

- `Jenkinsfile_teeproxy_build_docker_image` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES/job/teeproxy/job/build_docker_image/)):
Pipeline de création de l'image du [teeproxy](https://gitlab.socrate.vsct.fr/hesperides/teeproxy) pour publication sur le dépôt Docker interne :
construit l'image depuis le repo Gitlab, et la pushe vers Artifactory.

### Pipelines de release

- `Jenkinsfile_release` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES/job/continuous-delivery/job/release/)):
  * crée une _release branch_ sur le projet GitHub [hesperides](https://github.com/voyages-sncf-technologies/hesperides)
  * crée un release GitHub, et donc un tag `git`, sur le projet GitHub [hesperides](https://github.com/voyages-sncf-technologies/hesperides)
  * cette release doit déclencher la création d'une nouvelle image Docker taguée sur le [Dockerhub public](https://hub.docker.com/r/hesperides/hesperides/)
  * merge & push de cette release sur les branches `develop` & `master` (c'est le "git workflow" classique)
  * déclenche les 3 jobs `Jenkinsfile*_build_docker_image` pour generée les images Docker des 3 composants pour cette release

### Pipelines de déploiement

- `Jenkinsfile_deploy` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES/job/continuous-delivery/job/deploy/)):
  * déploie l'image Docker correspondante au YAML (_cf._ [Environments.md](Environments.md)) via [le job pprundeck HES/Outils/refresh_puppet_agent](https://pprundeck.socrate.vsct.fr/rundeck/project/HES/job/show/03662b77-5169-4828-96e8-8ba855d6c441)
  * exécute [le job pprundeck HES/Outils/RESTART](https://pprundeck.socrate.vsct.fr/rundeck/project/HES/job/show/c9f92ce5-2d20-4a57-9cb8-8e88aae5412f) qui effectue un `./SHUT && ./BOOT`

- `Jenkinsfile_deploy_nightly_dev` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES/job/continuous-delivery/job/deploy_nightly_dev/)):
déclenche le job `Jenkinsfile_deploy` sur `REL1` toutes les nuits

- `Jenkinsfile_deploy_nightly_int` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES/job/continuous-delivery/job/deploy_nightly_int/)):
déclenche le job `Jenkinsfile_deploy` sur `INT1` toutes les nuits

- `Jenkinsfile_deploy@PROD` ([job jenkins](https://master.jenkins.cloud.socrate.vsct.fr/job/A_HESPERIDES@PROD/job/deploy/)):
identique à `Jenkinsfile_deploy` mais sur `PRD1`

## Tips

Tester une image docker pushée dans le registry privé vsct :
```cmd
docker login --username admgit --password 'XXXXXXXXX' https://docker-vsct.pkg.cloud.socrate.vsct.fr
docker pull docker-vsct.pkg.cloud.socrate.vsct.fr/hesperides/vsct-hesperides:develop
```
