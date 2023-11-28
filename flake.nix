{
  description = "A Nix-flake-based Java 8 + Maven 3.5.4 dev env";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";

  outputs = { self, nixpkgs }:
    let
      javaVersion = 8;
      overlays = [
        (final: prev: {
          jdk = prev."jdk${toString javaVersion}_headless";
          maven = prev.stdenv.mkDerivation rec {
            name = "maven-3.5.4";
            src = prev.fetchurl {
              url = "https://archive.apache.org/dist/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz";
              sha256 = "sha256-zlCxyRNky3fv43dvdWptkrdtkDiwoHgvfVOs8emXoU0=";
            };
            buildInputs = [ prev.maven ];
            buildCommand = ''
              tar -xf $src
              mv apache-maven-3.5.4 $out
            '';
          };
        })
      ];
      supportedSystems = [ "x86_64-linux" "aarch64-linux" "x86_64-darwin" "aarch64-darwin" ];
      forEachSupportedSystem = f: nixpkgs.lib.genAttrs supportedSystems (system: f {
        pkgs = import nixpkgs { inherit overlays system; };
      });
    in
    {
      devShells = forEachSupportedSystem ({ pkgs }: {
        default = pkgs.mkShell {
          packages = with pkgs; [ jdk maven packer ];
          shellHook = ''
            echo "JOSSO CE / IAM.Tf : Java dev env ("${pkgs.jdk.name}" / ${pkgs.maven.name})"
            export JAVA_HOME="${pkgs.jdk}/lib/openjdk"
            export MAVEN_HOME="${pkgs.maven}"
            export MAVEN_OPTS="-Dmaven.test.skip=true -Xmx2048m"
          '';
        };
      });
    };
}
