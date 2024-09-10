{
  description = "Wrtr flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
  };

  outputs = { self, nixpkgs }:
  let
    system = "x86_64-linux";
    pkgs = nixpkgs.legacyPackages.${system};
  in
  {
    devShells.${system}.default =
      pkgs.mkShell 
        {
          buildInputs = [
            pkgs.stdenv.cc.cc.lib
            pkgs.nodejs_22
            pkgs.glib
          ];

          LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath [
            pkgs.stdenv.cc.cc.lib
            pkgs.glib
          ];

          shellHook = ''
            echo "Wrtr flake started"
          '';

        };

    #packages.x86_64-linux.hello = nixpkgs.legacyPackages.x86_64-linux.hello;

    #packages.x86_64-linux.default = self.packages.x86_64-linux.hello;


  };
}
