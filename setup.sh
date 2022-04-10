# install docker and curl
sudo apt install -y docker docker.io curl 
sudo usermod -aG docker $USER

# install Vagrant
curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -
sudo apt-add-repository "deb [arch=amd64] https://apt.releases.hashicorp.com $(lsb_release -cs) main"
sudo apt-get update && sudo apt-get install vagrant
newgrp docker << END

# cloning app inventor source & patch
git clone https://github.com/mit-cml/appinventor-sources.git
mv Vagrantfile appinventor-sources/Vagrantfile
cd appinventor-sources
echo "apt install -y git" >> bootstrap.sh
vagrant up --provider=docker
git submodule update --init
cd ..